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

        if (dto.getImagem64() != null && !dto.getImagem64().isEmpty()) {
            entity.setImagens(converteBase64ParaBinario(dto.getImagem64()));
        }
        return entity;
    }

    // Recebe uma entidade e retorna um objeto response DTO
    public TicketResponseDTO toDTO(Ticket entity){
        TicketResponseDTO dto = mapper.map(entity, TicketResponseDTO.class);

        if (entity.getImagens() != null && !entity.getImagens().isEmpty()) {
            dto.setImagem64(converteImagemParaBase64(entity.getImagens()));
        }

        return dto;
    }

    // Pega uma lista de e entidade (parametro) e retorna uma lista de response DTO
    public List<TicketResponseDTO> toDTO(List<Ticket> tickets){
        return tickets.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Converte uma lista de  byte[] para uma lista de Strings em base64
    private List<String> converteImagemParaBase64(List<byte[]> imagens) {
        return imagens.stream()
                .map(this::converteImagemParaBase64)
                .collect(Collectors.toList());
    }

    // Converte um binário para uma String em base64
    private String converteImagemParaBase64(byte[] imagem) {
        return Base64.getEncoder().encodeToString(imagem);
    }

    // Converte o base64 para binário
    private List<byte[]> converteBase64ParaBinario(List<String> imagens64) {
        return imagens64.stream()
                .map(this::converteBase64ParaBinario)
                .collect(Collectors.toList());
    }

    // Converte o base64 para binário
    private byte[] converteBase64ParaBinario(String imagem64){
        return Base64.getDecoder().decode(imagem64);
    }
}
