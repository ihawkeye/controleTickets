package com.innovate.controletickets.services;

import com.innovate.controletickets.exception.TecnicoNotFoundException;
import com.innovate.controletickets.model.Tecnico;
import com.innovate.controletickets.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TecnicoService {


    @Autowired
    private TecnicoRepository tecnicoRepository;

    public void salvar(Tecnico tecnico) {
        tecnicoRepository.save(tecnico);
    }

    public List<Tecnico> listarTodos() {
        return tecnicoRepository.findAll();
    }

    public Optional<Tecnico> buscarPorNome(String nome) {
        return tecnicoRepository.findByNome(nome);
    }

    public UUID buscarIdTecnicoPorNome(String nomeTecnico) throws TecnicoNotFoundException {
        Tecnico tecnico = tecnicoRepository.findByNome(nomeTecnico)
                .orElseThrow(() -> new TecnicoNotFoundException("Técnico com nome '" + nomeTecnico + "' não encontrado"));
        return tecnico.getId();
    }


}
