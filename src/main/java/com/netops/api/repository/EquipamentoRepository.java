package com.netops.api.repository;

import com.netops.api.model.Equipamento;
import com.netops.api.model.StatusEquipamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

    Optional<Equipamento> findByMacAddress(String macAddress);

    List<Equipamento> findByStatus(StatusEquipamento status);

}