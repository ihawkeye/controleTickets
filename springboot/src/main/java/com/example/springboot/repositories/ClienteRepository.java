package com.example.springboot.repositories;

import com.example.springboot.models.ClienteModel;

import org.springframework.data.jpa.repository.JpaRepository;



public interface ClienteRepository extends JpaRepository<ClienteModel, Integer> {
}
