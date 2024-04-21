package com.example.springboot.controllers;


import com.example.springboot.dtos.TecnicoRecordDto;
import com.example.springboot.models.TecnicoModel;
import com.example.springboot.repositories.TecnicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TecnicoController {
    @Autowired
    TecnicoRepository tecnicoRepository;
    @PostMapping("/Tecnico")
    public ResponseEntity<TecnicoModel> saveTecnico(@RequestBody @Valid TecnicoRecordDto tecnicoRecordDto){
        var tecnicoModel = new TecnicoModel();
        BeanUtils.copyProperties(tecnicoRecordDto, tecnicoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoRepository.save(tecnicoModel));

    }


}
