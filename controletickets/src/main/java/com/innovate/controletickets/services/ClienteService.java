package com.innovate.controletickets.services;

import com.innovate.controletickets.exception.ClienteNotFoundException;
import com.innovate.controletickets.model.Cliente;
import com.innovate.controletickets.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente gravar(Cliente clienteDTO) {
        Optional<Cliente> clienteExistente = clienteRepository.findBySerial(clienteDTO.getSerial());
        if (clienteExistente.isPresent()) {
            return clienteExistente.get();
        } else {
            Cliente novoCliente = new Cliente(); // Renomeando a variável aqui
            novoCliente.setSerial(clienteDTO.getSerial());
            novoCliente.setNome(clienteDTO.getNome());
            return clienteRepository.save(novoCliente);
        }
    }

    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(UUID id) throws ClienteNotFoundException {
        Optional<Cliente> opt = clienteRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new ClienteNotFoundException("Cliente com id: " + id + " não encontrado");
        }
    }

    public Cliente buscarClientePorSerial(String serial) throws ClienteNotFoundException {
        Optional<Cliente> opt = clienteRepository.findBySerial(serial);
        if (opt.isPresent()){
            return opt.get();
        } else {
            throw new ClienteNotFoundException("Cliente com serial: " + serial + " não encontrado");
        }
    }

    public Cliente buscarClientePorNome(String nome) throws ClienteNotFoundException{
        Optional<Cliente> opt= clienteRepository.findByNome(nome);
        if (opt.isPresent()){
            return opt.get();
        } else {
            throw new ClienteNotFoundException(null);
        }
    }


    public Cliente alterarCliente(UUID id, Cliente cliente) throws ClienteNotFoundException {
        Cliente clienteGravado = buscarClientePorId(id);
        clienteGravado.setNome(cliente.getNome());
        clienteGravado.setSerial(cliente.getSerial());
        clienteGravado.setAtivo(cliente.getAtivo());
        return clienteRepository.save(clienteGravado);
    }

    public void apagarCliente(UUID id) throws ClienteNotFoundException {
        Cliente cliente = buscarClientePorId(id);
        clienteRepository.delete(cliente);
    }


}
