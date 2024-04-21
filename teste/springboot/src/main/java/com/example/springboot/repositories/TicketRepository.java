package com.example.springboot.repositories;

import com.example.springboot.models.TicketModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketRepository extends JpaRepository<TicketModel, UUID> {
}
