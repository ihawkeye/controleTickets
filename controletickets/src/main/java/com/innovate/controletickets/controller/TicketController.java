package com.innovate.controletickets.controller;

import com.innovate.controletickets.dto.TicketCreateDTO;
import com.innovate.controletickets.dto.TicketMapper;
import com.innovate.controletickets.dto.TicketResponseDTO;
import com.innovate.controletickets.exception.TicketNotFoundException;
import com.innovate.controletickets.model.Ticket;
import com.innovate.controletickets.repository.TicketRepository;
import com.innovate.controletickets.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private TicketRepository ticketRepository;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody TicketCreateDTO ticketCreateDTO) {
        try {
            Ticket ticketGravado = ticketService.gravarTicket(ticketCreateDTO);
            TicketResponseDTO ticketResponseDTO = ticketMapper.toDTO(ticketGravado);
            return ResponseEntity.status(HttpStatus.CREATED).body(ticketResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar ticket");
        }
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> buscarTodos() {
        List<Ticket> tickets = ticketService.buscarTodos();
        List<TicketResponseDTO> ticketResponseDTOs = ticketMapper.toDTO(tickets);
        return ResponseEntity.status(HttpStatus.OK).body(ticketResponseDTOs);
    }

    @GetMapping("/prioridades")
    public ResponseEntity<List<TicketResponseDTO>> prioridades() {
        List<Ticket> tickets = ticketService.buscarPrioridades();
        List<TicketResponseDTO> ticketResponseDTOs = ticketMapper.toDTO(tickets);
        return ResponseEntity.status(HttpStatus.OK).body(ticketResponseDTOs);
    }

    @GetMapping("/naosolucionado")
    public ResponseEntity<List<TicketResponseDTO>> naoSolucionado() {
        List<Ticket> tickets = ticketService.buscarNaoSolucionado();
        List<TicketResponseDTO> ticketResponseDTOs = ticketMapper.toDTO(tickets);
        return ResponseEntity.status(HttpStatus.OK).body(ticketResponseDTOs);
    }

    @GetMapping("/solucionado")
    public ResponseEntity<List<TicketResponseDTO>> solucionado() {
        List<Ticket> tickets = ticketService.buscarSolucionado();
        List<TicketResponseDTO> ticketResponseDTOs = ticketMapper.toDTO(tickets);
        return ResponseEntity.status(HttpStatus.OK).body(ticketResponseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTicketPorId(@PathVariable(value = "id") UUID id) {

        try {
            Ticket ticketGravado = ticketService.buscarTicketPorId(id);
            TicketResponseDTO ticketResponseDTO = ticketMapper.toDTO(ticketGravado);
            return ResponseEntity.status(HttpStatus.OK).body(ticketResponseDTO);
        } catch (TicketNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/serial/{serial}")
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
    public ResponseEntity<?> alterar(@PathVariable(value = "id") UUID id, @RequestBody Map<String, Object> dadosAtualizados) {
        try {
            TicketResponseDTO ticketAtualizado = ticketService.alterarTicket(id, dadosAtualizados);
            return ResponseEntity.status(HttpStatus.OK).body(ticketAtualizado);
        } catch (TicketNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/interact")
    public ResponseEntity<TicketResponseDTO> atualizarUltimaInteracao(@PathVariable(value = "id") UUID id) {
        try {
            Date dataAtual = Date.valueOf(LocalDate.now());
            TicketResponseDTO ticketAtualizado = ticketService.atualizarUltimaInteracao(id, dataAtual);
            return ResponseEntity.ok(ticketAtualizado);
        } catch (TicketNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> apagar(@PathVariable(value = "id") UUID id) {

        try {
            ticketService.apagarTicket(id);
            return ResponseEntity.status(HttpStatus.OK).body("Removido com sucesso");
        } catch (TicketNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //aqui os upload das imagens e método get
    @PostMapping("/{id}/uploadImg")
    public ResponseEntity<?> uploadImg(@PathVariable UUID id, @RequestParam("file") MultipartFile file) throws IOException {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try{
            Ticket ticket = optionalTicket.get();
            ticket.setImagem(file.getBytes());
            ticketRepository.save(ticket);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer upload: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + e.getMessage());
        }

    }
    // ainda falta resolver o front, acredito que seja só linkar a imagem upada ao btn ticket
    @GetMapping("/{id}/getImg")
    public ResponseEntity<byte[]> getImagem(@PathVariable UUID id) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Ticket ticket = optionalTicket.get();
        byte[] imagem = ticket.getImagem();

        if (imagem == null || imagem.length == 0) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagem, headers, HttpStatus.OK);
    }
}

