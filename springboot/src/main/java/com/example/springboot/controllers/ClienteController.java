package com.example.springboot.controllers;


import com.example.springboot.dtos.ClienteRecordDto;
import com.example.springboot.models.ClienteModel;
import com.example.springboot.repositories.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {
    @Autowired
    ClienteRepository clienteRepository;
    @PostMapping("/Cliente")
    public ResponseEntity<ClienteModel> saveCliente(@RequestBody @Valid ClienteRecordDto clienteRecordDto){
        var clienteModel = new ClienteModel();
        BeanUtils.copyProperties(clienteRecordDto, clienteModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(clienteModel));

    }
}
