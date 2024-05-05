package com.innovate.controletickets.repository;

import com.innovate.controletickets.model.Cliente;
import com.innovate.controletickets.model.Ticket;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Optional<Ticket> findByNumero(String numero);
    List<Ticket> findByCliente(Cliente cliente);

    @Query("SELECT t FROM Ticket t WHERE t.cliente.serial = :serial")
    List<Ticket> findByClienteSerial(@Param("serial") String serial);
}
