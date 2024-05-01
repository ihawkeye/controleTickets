package com.innovate.controletickets.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public class ClienteCreateDTO {

    @NotBlank
    private String nome;

    @NotBlank
    @Size(min = 6, max = 6, message = "Precisa ter 6 caracteres")
    private String serial;

    private Boolean ativo = true;

    //Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
