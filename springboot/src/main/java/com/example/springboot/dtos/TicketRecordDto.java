package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;


public record TicketRecordDto( @NotBlank String name,
                               @NotNull Integer idTicket,
                               @NotNull Integer idCliente,
                               @NotBlank String tipo,
                               @NotBlank String prioridade,
                               @NotNull Integer numero,
                               @NotNull Integer sac,
                               @NotNull Date dataOcorrencia,
                               @NotNull Date dataUltimaInteracao,
                               @NotBlank String categoria,
                               @NotNull Date dataUltimoTeste,
                               @NotBlank String ultimaVersao,
                               @NotBlank String status,
                               @NotNull boolean vinicius,
                               @NotBlank String ocorrencia,
                               @NotBlank String observacao)
{}
