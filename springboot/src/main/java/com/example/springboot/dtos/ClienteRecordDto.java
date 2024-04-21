package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteRecordDto(@NotBlank Integer id, @NotBlank String nome, @NotNull Boolean ativo) {
}
