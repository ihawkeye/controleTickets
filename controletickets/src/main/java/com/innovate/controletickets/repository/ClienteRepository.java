package com.innovate.controletickets.repository;

import com.innovate.controletickets.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Cliente findBySerial(String serial);
    Cliente findByNome(String nome);
    List<Cliente> findByNomeContaining(String nome);
}
