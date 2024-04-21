package com.example.springboot.controllers;

import com.example.springboot.dtos.TicketRecordDto;
import com.example.springboot.models.TicketModel;
import com.example.springboot.repositories.TicketRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
    @Autowired
    TicketRepository ticketRepository;
    @PostMapping("/Ticket")
    public ResponseEntity<TicketModel>saveTicket(@RequestBody @Valid TicketRecordDto ticketRecordDto){
        var ticketModel = new TicketModel();
        BeanUtils.copyProperties(ticketRecordDto, ticketModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketRepository.save(ticketModel));

    }


}
