package com.example.springboot.repositories;

import com.example.springboot.models.TicketModel;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<TicketModel, Integer> {
}
