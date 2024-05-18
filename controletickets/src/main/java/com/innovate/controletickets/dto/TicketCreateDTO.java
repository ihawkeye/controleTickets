package com.innovate.controletickets.dto;

import com.innovate.controletickets.model.Cliente;
import com.innovate.controletickets.model.Tecnico;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Date;
import java.util.UUID;

public class TicketCreateDTO {

    @ManyToOne
    @JoinColumn(name = "idcliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idtecnico")
    private Tecnico tecnico;

    private String tipo;

    private String prioridade;

    private String numero;

    private String sac;

    private Date dataOcorrencia;

    private Date dataUltimaInteracao;

    private String categoria;

    private Date dataUltimoTeste;

    private String ultimaVersao;

    private String status;

    private Boolean vinicius;

    @NotBlank
    @Column(columnDefinition = "TEXT") // define o tipo no banco como "Text"
    private String ocorrencia;

    @Column(columnDefinition = "TEXT") // define o tipo no banco como "Text"
    private String observacao;

    @Lob
    //@Column(columnDefinition = "BYTEA")
    private byte[] imagem;
    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public TicketCreateDTO() {
    }


// Getters and Setters

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSac() {
        return sac;
    }

    public void setSac(String sac) {
        this.sac = sac;
    }

    public Date getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(Date dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public Date getDataUltimaInteracao() {
        return dataUltimaInteracao;
    }

    public void setDataUltimaInteracao(Date dataUltimaInteracao) {
        this.dataUltimaInteracao = dataUltimaInteracao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Date getDataUltimoTeste() {
        return dataUltimoTeste;
    }

    public void setDataUltimoTeste(Date dataUltimoTeste) {
        this.dataUltimoTeste = dataUltimoTeste;
    }

    public String getUltimaVersao() {
        return ultimaVersao;
    }

    public void setUltimaVersao(String ultimaVersao) {
        this.ultimaVersao = ultimaVersao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getVinicius() {
        return vinicius;
    }

    public void setVinicius(Boolean vinicius) {
        this.vinicius = vinicius;
    }

    public String getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(String ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
