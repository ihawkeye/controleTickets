package com.innovate.controletickets.dto;

import com.innovate.controletickets.model.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteMapper {

    @Autowired
    private ModelMapper mapper;

    // Recebe um objeto response DTO do cliente e transforma em uma entidade.
    public Cliente toEntity(ClienteCreateDTO dto) {
        Cliente entity = mapper.map(dto, Cliente.class); // pega o dto e transforma em uma classe estudante
        return entity;
    }

    // Recebe uma entidade e retorna um objeto respose DTO
    public ClienteResponseDTO toDTO(Cliente entity) {
        ClienteResponseDTO dto = mapper.map(entity, ClienteResponseDTO.class );
        return dto;
    }

    // pega uma lista de entidade (parametro) e retorna uma lista de response DTO
    public List<ClienteResponseDTO> toDTO(List<Cliente> clientes){
        return clientes.stream()
                .map(cliente -> toDTO(cliente))
                .collect(Collectors.toList());
    }

}