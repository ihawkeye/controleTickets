package com.innovate.controletickets.dto;

import com.innovate.controletickets.model.Ticket;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketMapper {

    @Autowired
    private ModelMapper mapper;

    // Recebe um objeto response DTO do cliente e transforma em uma entidade.
    public Ticket toEntity(TicketCreateDTO dto){
        Ticket entity = mapper.map(dto, Ticket.class);
        return entity;
    }

    // Recebe uma entidade e retorna um objeto respose DTO
    public TicketResponseDTO toDTO(Ticket entity){
        TicketResponseDTO dto = mapper.map(entity, TicketResponseDTO.class);
        return dto;
    }

    // Pega uma lista de e entidade (parametro) e retorna uma lista de response DTO
    public List<TicketResponseDTO> toDTO(List<Ticket> tickets){
        return tickets.stream()
                .map(ticket -> toDTO(ticket))
                .collect(Collectors.toList());
    }
}
