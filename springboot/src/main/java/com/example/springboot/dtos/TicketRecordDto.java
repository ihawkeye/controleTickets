package com.example.springboot.dtos;

//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
import java.sql.Date;


public record TicketRecordDto(  String name,
                                Integer idTicket,
                                Integer idCliente,
                                String tipo,
                                String prioridade,
                                Integer numero,
                                Integer sac,
                                Date dataOcorrencia,
                                Date dataUltimaInteracao,
                                String categoria,
                                Date dataUltimoTeste,
                                String ultimaVersao,
                                String status,
                                boolean vinicius,
                                String ocorrencia,
                                String observacoes)
{}
