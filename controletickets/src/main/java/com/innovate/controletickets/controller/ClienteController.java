package com.innovate.controletickets.controller;

import com.innovate.controletickets.dto.*;
import com.innovate.controletickets.exception.ClienteNotFoundException;
import com.innovate.controletickets.model.Cliente;
import com.innovate.controletickets.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cliente") // mapeia a rota clientes. ao chamar essa rota sera tratada por esse controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteMapper clienteMapper;

    // ClienteCreateDTO precisa estar em conformidade com o obj do front. To entity instancia a classe cliente. Depois aciona a função de gravar do clienteService.
    @PostMapping
    public ResponseEntity<?> gravar(@RequestBody ClienteCreateDTO clienteCreateDTO) {
            Cliente cliente = clienteMapper.toEntity(clienteCreateDTO);
            Cliente clienteGravado = clienteService.gravar(cliente);
            ClienteResponseDTO clienteResponseDTO = clienteMapper.toDTO(clienteGravado);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> buscarTodos(){
        List<Cliente> clientes = clienteService.buscarTodos();
        List<ClienteResponseDTO> clienteResponseDTOS = clienteMapper.toDTO(clientes);
        return ResponseEntity.status(HttpStatus.OK).body(clienteResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarUm(@PathVariable(value = "id") UUID id) {

        try{
            Cliente clienteGravado = clienteService.buscarClientePorId(id);
            ClienteResponseDTO clienteResponseDTO = clienteMapper.toDTO(clienteGravado);
            return ResponseEntity.status(HttpStatus.OK).body(clienteResponseDTO);
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@PathVariable(value = "id") UUID id, @RequestBody ClienteCreateDTO clienteCreateDTO) {

        try {
            Cliente cliente = clienteMapper.toEntity(clienteCreateDTO);
            Cliente clienteGravado = clienteService.alterarCliente(id, cliente);
            ClienteResponseDTO clienteResponseDTO = clienteMapper.toDTO(clienteGravado);
            return ResponseEntity.status(HttpStatus.OK).body(clienteResponseDTO);
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> apagar(@PathVariable(value = "id") UUID id) {
        try {
            clienteService.apagarCliente(id);
            return ResponseEntity.status(HttpStatus.OK).body("Removido com sucesso");
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
