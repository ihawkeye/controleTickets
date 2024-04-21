package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;

public record TicketRecordDto( String name, Integer id,  Integer idCliente, Integer idTecnico,
                              @NotBlank String tipo, @NotBlank String prioridade,
                              int numero, Integer sac, Date dataOcorrencia,
                              Date dataUltimaInteracao, @NotBlank String categoria,
                              Date dataUltimoTeste,
                              @NotBlank String ultimaVersao,
                              @NotBlank String status,
                              boolean vinicius, @NotBlank String ocorrencia,
                              @NotBlank String observacao)
{}
