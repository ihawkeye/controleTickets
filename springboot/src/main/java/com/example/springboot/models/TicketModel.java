package com.example.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.sql.Date;



@Entity
@Table(name = "TB_TICKET")
public class TicketModel implements Serializable {
    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idTicket;
    private String name;


    @JoinColumn(name = "id_cliente")
    private Integer idCliente;




    private String tipo;
    private String prioridade;
    private Integer numero;
    private Integer sac;
    private Date dataOcorrencia;
    private Date dataUltimaInteracao;
    private String categoria;
    private Date dataUltimoTeste;
    private String ultimaVersao;
    private String status;
    private boolean vinicius;
    private String ocorrencia;
    private String observacao;

    //get set


    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Integer getSac() {
        return sac;
    }

    public void setSac(Integer sac) {
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

    public boolean isVinicius() {
        return vinicius;
    }

    public void setVinicius(boolean vinicius) {
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
