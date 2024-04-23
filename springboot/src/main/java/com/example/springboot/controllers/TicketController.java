package com.example.springboot.controllers;

import com.example.springboot.dtos.TicketRecordDto;
import com.example.springboot.models.TicketModel;
import com.example.springboot.repositories.TicketRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TicketController {

    //Método Post
    @Autowired
    TicketRepository ticketRepository;
    @PostMapping("/Ticket")
    public ResponseEntity<TicketModel>saveTicket(@RequestBody @Valid TicketRecordDto ticketRecordDto){
        var ticketModel = new TicketModel();
        BeanUtils.copyProperties(ticketRecordDto, ticketModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketRepository.save(ticketModel));

    }

    //Método get all, método de leitura

    @GetMapping("/Ticket")
    public ResponseEntity<List<TicketModel>> getAllTicket(){
        return ResponseEntity.status(HttpStatus.OK).body(ticketRepository.findAll());
    }

    //Método get one, leitura específica
    @GetMapping("/Ticket/{idTicket}")
    public ResponseEntity<Object>getOneTicket(@PathVariable("idTicket")Integer idTicket){
        Optional<TicketModel> ticketO = ticketRepository.findById(idTicket);
        return ticketO.<ResponseEntity<Object>>map(ticketModel -> ResponseEntity.status(HttpStatus.OK).body(ticketModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket não encontrado"));
    }

    //Método Put, método de atualização
    @PutMapping("/Ticket/{idTicket}")
    public ResponseEntity<Object>updateTicket(@PathVariable ("idTicket")Integer idTicket,
                                               @RequestBody @Valid TicketRecordDto ticketRecordDto){
        Optional<TicketModel> ticketO = ticketRepository.findById(idTicket);
        if(ticketO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id ticket não encontrado.");
        }
        var ticketModel = ticketO.get();
        BeanUtils.copyProperties(ticketRecordDto, ticketModel);
        return ResponseEntity.status(HttpStatus.OK).body(ticketRepository.save(ticketModel));
    }


    //Método DELETE
    @DeleteMapping("/Ticket/{idTicket}")
    public ResponseEntity<Object> deleteTicket(@PathVariable("idTicket")Integer idTicket){
        Optional<TicketModel> ticketO = ticketRepository.findById(idTicket);
        if(ticketO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("idTicket não encontrado");
        }
        ticketRepository.delete(ticketO.get());
        return ResponseEntity.status(HttpStatus.OK).body("idTicket deletado com sucesso");
    }




}
