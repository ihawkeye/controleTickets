package com.example.springboot.repositories;

import com.example.springboot.models.TecnicoModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TecnicoRepository extends JpaRepository<TecnicoModel, Integer> {
}

