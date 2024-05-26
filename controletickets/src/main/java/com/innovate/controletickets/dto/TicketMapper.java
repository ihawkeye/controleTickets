package com.innovate.controletickets.dto;

import com.innovate.controletickets.model.Ticket;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketMapper {

    @Autowired
    private ModelMapper mapper;

    // Recebe um objeto response DTO do cliente e transforma em uma entidade.
    public Ticket toEntity(TicketCreateDTO dto){
        Ticket entity = mapper.map(dto, Ticket.class);

        if (dto.getImagem64() != null) {
            entity.setImagem(converteBase64paraBinario(dto.getImagem64()));
        }
        return entity;
    }

    // Recebe uma entidade e retorna um objeto response DTO
    public TicketResponseDTO toDTO(Ticket entity){
        TicketResponseDTO dto = mapper.map(entity, TicketResponseDTO.class);

        if (entity.getImagem() != null) {
            dto.setImagem64(converteImagemParaBase64(entity.getImagem()));
        }

        return dto;
    }

    // Pega uma lista de e entidade (parametro) e retorna uma lista de response DTO
    public List<TicketResponseDTO> toDTO(List<Ticket> tickets){
        return tickets.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    //aqui converte imagem(que está como byte[] no bd) para base64
    private String converteImagemParaBase64(byte[] imagem) {
        return Base64.getEncoder().encodeToString(imagem);
    }

    //converte o base64 para binário
    private byte[] converteBase64paraBinario(String imagem64){return Base64.getDecoder().decode(imagem64);}

}
