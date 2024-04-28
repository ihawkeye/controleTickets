package com.innovate.controletickets.services;

import com.innovate.controletickets.exception.TicketNotFoundException;
import com.innovate.controletickets.model.Ticket;
import com.innovate.controletickets.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket gravar(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    public List<Ticket> buscarTodos(){
        return ticketRepository.findAll();
    }

    public Ticket buscarTicketPorId(UUID id) throws TicketNotFoundException{
        Optional<Ticket> opt = ticketRepository.findById(id);
        if (opt.isPresent()){
            return opt.get();
        } else {
            throw new TicketNotFoundException("Ticket com id: " + id + " não foi encontrado");
        }
    }

    public Ticket alterarTicket(UUID id, Ticket ticket) throws TicketNotFoundException{
        Ticket ticketGravado = buscarTicketPorId(id);
        ticketGravado.setTipo(ticket.getTipo());
        ticketGravado.setPrioridade(ticket.getPrioridade());
        ticketGravado.setNumero(ticket.getNumero());
        ticketGravado.setSac(ticket.getSac());
        ticketGravado.setDataOcorrencia(ticket.getDataOcorrencia());
        ticketGravado.setDataUltimaInteracao(ticket.getDataUltimaInteracao());
        ticketGravado.setCategoria(ticket.getCategoria());
        ticketGravado.setDataUltimoTeste(ticket.getDataUltimoTeste());
        ticketGravado.setUltimaVersao(ticket.getUltimaVersao());
        ticketGravado.setStatus(ticket.getStatus());
        ticketGravado.setVinicius(ticket.getVinicius());
        ticketGravado.setOcorrencia(ticket.getOcorrencia());
        ticketGravado.setObservacao(ticket.getObservacao());
        return ticketRepository.save(ticketGravado);
    }

    public void apagarTicket(UUID id) throws TicketNotFoundException{
        Ticket ticket = buscarTicketPorId(id);
        ticketRepository.delete(ticket);
    }

}
