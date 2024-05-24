package com.innovate.controletickets.services;

import com.innovate.controletickets.dto.TicketCreateDTO;
import com.innovate.controletickets.dto.TicketMapper;
import com.innovate.controletickets.dto.TicketResponseDTO;
import com.innovate.controletickets.exception.TicketNotFoundException;
import com.innovate.controletickets.model.Cliente;
import com.innovate.controletickets.model.Tecnico;
import com.innovate.controletickets.model.Ticket;
import com.innovate.controletickets.repository.ClienteRepository;
import com.innovate.controletickets.repository.TecnicoRepository;
import com.innovate.controletickets.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

        // Decodifica a imagem, se presente, e a define no ticket
        if (ticketCreateDTO.getImagem64() != null) {
            byte[] imagem = Base64.getDecoder().decode(ticketCreateDTO.getImagem64());
            ticket.setImagem(imagem);
        }


        ticket = ticketRepository.save(ticket);

        return ticketRepository.save(ticket);
    }

    public List<Ticket> buscarTodos(){
        Sort sortByDataOcorrencia = Sort.by("dataOcorrencia").ascending();
        return ticketRepository.findAll(sortByDataOcorrencia);
    }

    public Ticket buscarTicketPorId(UUID id) throws TicketNotFoundException{
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isPresent()){
            return ticket.get();
        } else {
            throw new TicketNotFoundException("Ticket com id: " + id + " não foi encontrado");
        }
    }

    public List<Ticket> buscarPrioridades() {
        LocalDate dataLimite = LocalDate.now().minusDays(7); // Data limite é há 7 dias atrás
        return ticketRepository.findPrioridades(dataLimite);
    }

    public List<Ticket> buscarNaoSolucionado() {
        return ticketRepository.findNaoSolucionado();
    }

    public List<Ticket> buscarSolucionado() {
        return ticketRepository.findSolucionado();
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

    public TicketResponseDTO atualizarUltimaInteracao(UUID id, Date dataAtual) throws TicketNotFoundException {
        Ticket ticketGravado = buscarTicketPorId(id);
        ticketGravado.setDataUltimaInteracao(dataAtual);
        Ticket ticketAtualizado = ticketRepository.save(ticketGravado);
        return ticketMapper.toDTO(ticketAtualizado);
    }

    public TicketResponseDTO alterarTicket(UUID id, Map<String, Object> dadosAtualizados) throws TicketNotFoundException {
        Ticket ticketGravado = buscarTicketPorId(id);

        if (dadosAtualizados.containsKey("ultimaVersao")) {
            ticketGravado.setUltimaVersao((String) dadosAtualizados.get("ultimaVersao"));
        }
        if (dadosAtualizados.containsKey("status")) {
            ticketGravado.setStatus((String) dadosAtualizados.get("status"));
        }
        if (dadosAtualizados.containsKey("dataUltimoTeste")) {
            String dataString = (String) dadosAtualizados.get("dataUltimoTeste");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                java.util.Date parsedDate = sdf.parse(dataString);
                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                ticketGravado.setDataUltimoTeste(sqlDate);
            } catch (Exception e) {
                return new TicketResponseDTO(); // qualquer coisa volta o constructor vazio
            }
        }
        if (dadosAtualizados.containsKey("vinicius")) {
            ticketGravado.setVinicius((Boolean) dadosAtualizados.get("vinicius"));
        }
        if (dadosAtualizados.containsKey("ocorrencia")) {
            ticketGravado.setOcorrencia((String) dadosAtualizados.get("ocorrencia"));
        }
        if (dadosAtualizados.containsKey("observacao")) {
            ticketGravado.setObservacao((String) dadosAtualizados.get("observacao"));
        }

        Ticket ticketAtualizado = ticketRepository.save(ticketGravado);
        return ticketMapper.toDTO(ticketAtualizado);
    }

    public void apagarTicket(UUID id) throws TicketNotFoundException{
        Ticket ticket = buscarTicketPorId(id);
        ticketRepository.delete(ticket);
    }

}