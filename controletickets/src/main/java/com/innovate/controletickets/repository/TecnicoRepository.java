package com.innovate.controletickets.repository;

import com.innovate.controletickets.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TecnicoRepository extends JpaRepository<Tecnico, UUID> {
    Optional<Tecnico> findByNome(String nome);
}
