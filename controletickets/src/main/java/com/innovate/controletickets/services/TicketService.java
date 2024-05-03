package com.innovate.controletickets.services;

import com.innovate.controletickets.dto.TicketCreateDTO;
import com.innovate.controletickets.dto.TicketMapper;
import com.innovate.controletickets.exception.TicketNotFoundException;
import com.innovate.controletickets.model.Cliente;
import com.innovate.controletickets.model.Tecnico;
import com.innovate.controletickets.model.Ticket;
import com.innovate.controletickets.repository.ClienteRepository;
import com.innovate.controletickets.repository.TecnicoRepository;
import com.innovate.controletickets.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    public Ticket gravarTicket(TicketCreateDTO ticketCreateDTO){

        Cliente cliente = clienteRepository.findBySerial(ticketCreateDTO.getCliente().getSerial());
        if (cliente == null) {
            cliente = clienteService.gravar(ticketCreateDTO.getCliente());
        }

        Tecnico tecnico = tecnicoRepository.findByNome(ticketCreateDTO.getTecnico().getNome());

        Ticket ticket = ticketMapper.toEntity(ticketCreateDTO);

        ticket.setCliente(cliente);
        ticket.setTecnico(tecnico);
        ticket.setUltimaVersao(ticketCreateDTO.getUltimaVersao());

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

    //Retorna apenas 1 ticket
    public Ticket buscarTicketPorNumero(String numero) throws TicketNotFoundException{
        Optional <Ticket> opt = ticketRepository.findByNumero(numero);
        if (opt.isPresent()){
            return opt.get();
        } else {
            throw new TicketNotFoundException("Ticket com número: " + numero + " não foi encontrado");
        }
    }

    //Retorna mais de um ticket. (Preciso instanciar a classe cliente) (pensar se é melhor passar o obj ou o atr)!
    public List<Ticket> buscarTicketPorSerialCliente(String serial) throws TicketNotFoundException {
        // busca o cliente pelo número do serial
        Cliente cliente = clienteRepository.findBySerial(serial);
        // busca os tickets associados a esse cliente
        return ticketRepository.findByCliente(cliente);
    }

    // a resolver
    //public List<Ticket> buscarTicketsPorNomeCliente(String nome) throws TicketNotFoundException {
    //    Cliente cliente = clienteRepository.findByNome(nome)
    //            .orElseThrow(() -> new TicketNotFoundException("Cliente com nome '" + nome + "' não encontrado"));
    //    return ticketRepository.findByCliente(cliente);
    //}

    //Retorna mais de um ticket. (Preciso instanciar a classe cliente) (pensar se é melhor passar o obj ou o atr)!
    public List<Ticket> buscarTicketsPorNomeCliente(String nome) throws TicketNotFoundException {
        // Consulta os clientes que tenham parte do nome especificado
        List<Cliente> clientes = clienteRepository.findByNomeContaining(nome);

        if (clientes.isEmpty()) {
            throw new TicketNotFoundException("Nenhum cliente encontrado com o nome contendo '" + nome + "'");
        }

        // Lista para armazenar os tickets encontrados
        List<Ticket> ticketsEncontrados = new ArrayList<>();

        // Para cada cliente encontrado, busca seus tickets e adiciona à lista de tickets encontrados
        for (Cliente cliente : clientes) {
            List<Ticket> ticketsCliente = ticketRepository.findByCliente(cliente);
            ticketsEncontrados.addAll(ticketsCliente);
        }

        return ticketsEncontrados;
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