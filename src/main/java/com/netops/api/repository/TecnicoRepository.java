package com.netops.api.repository;

import com.netops.api.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {


}