package com.innovate.controletickets.controller;

import com.innovate.controletickets.dto.TicketCreateDTO;
import com.innovate.controletickets.dto.TicketMapper;
import com.innovate.controletickets.dto.TicketResponseDTO;
import com.innovate.controletickets.exception.TicketNotFoundException;
import com.innovate.controletickets.model.Tecnico;
import com.innovate.controletickets.model.Ticket;
import com.innovate.controletickets.services.TicketService;
import com.innovate.controletickets.services.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketMapper ticketMapper;

    @PostMapping
    public ResponseEntity<TicketResponseDTO> salvar(@RequestBody TicketCreateDTO ticketCreateDTO){
        Ticket ticket = ticketMapper.toEntity(ticketCreateDTO);
        Ticket ticketGravado = ticketService.gravar(ticket);
        TicketResponseDTO ticketResponseDTO = ticketMapper.toDTO(ticketGravado);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> buscarTodos(){
        List<Ticket> tickets = ticketService.buscarTodos();
        List<TicketResponseDTO> ticketResponseDTOs = ticketMapper.toDTO(tickets);
        return ResponseEntity.status(HttpStatus.OK).body(ticketResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarUm(@PathVariable (value = "id") UUID id){

        try{
            Ticket ticketGravado = ticketService.buscarTicketPorId(id);
            TicketResponseDTO ticketResponseDTO = ticketMapper.toDTO(ticketGravado);
            return ResponseEntity.status(HttpStatus.OK).body(ticketResponseDTO);
        } catch (TicketNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{serial}")
    public ResponseEntity<Object> buscarTicketsPorSerialCliente(@PathVariable String serial) {
        try {
            List<Ticket> tickets = ticketService.buscarTicketPorSerialCliente(serial);
            List<TicketResponseDTO> ticketResponseDTOs = ticketMapper.toDTO(tickets);
            return ResponseEntity.ok(ticketResponseDTOs);
        } catch (TicketNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/ticketsPorNomeCliente")
    public ResponseEntity<List<TicketResponseDTO>> buscarTicketsPorNomeCliente(@RequestParam String nome) {
        try {
            List<Ticket> tickets = ticketService.buscarTicketsPorNomeCliente(nome);
            List<TicketResponseDTO> ticketResponseDTOs = ticketMapper.toDTO(tickets);
            return ResponseEntity.ok(ticketResponseDTOs);
        } catch (TicketNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterar(@PathVariable(value = "id")UUID id, @RequestBody TicketCreateDTO ticketCreateDTO){

        try {
            Ticket ticket = ticketMapper.toEntity(ticketCreateDTO);
            Ticket ticketGravado = ticketService.alterarTicket(id, ticket);
            TicketResponseDTO ticketResponseDTO = ticketMapper.toDTO(ticketGravado);
            return ResponseEntity.status(HttpStatus.OK).body(ticketResponseDTO);
        } catch (TicketNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> apagar(@PathVariable(value = "id") UUID id){

        try {
            ticketService.apagarTicket(id);
            return ResponseEntity.status(HttpStatus.OK).body("Removido com sucesso");
        } catch (TicketNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
