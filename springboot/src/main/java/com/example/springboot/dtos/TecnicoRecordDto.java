package com.example.springboot.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record TecnicoRecordDto(@NotNull Integer id,@NotBlank String nome, @NotNull Boolean ativo) { }
