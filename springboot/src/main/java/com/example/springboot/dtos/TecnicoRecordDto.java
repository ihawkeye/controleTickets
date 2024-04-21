package com.example.springboot.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record TecnicoRecordDto(Integer id,String nome, Boolean ativo) { }
