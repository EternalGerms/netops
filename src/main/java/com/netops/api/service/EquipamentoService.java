package com.netops.api.service;

import com.netops.api.model.Equipamento;
import com.netops.api.model.StatusEquipamento;
import com.netops.api.repository.EquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;

    public List<Equipamento> listarTudo() {
        return equipamentoRepository.findAll();
    }

    public Equipamento salvar(Equipamento equipamento) {
        return equipamentoRepository.save(equipamento);
    }

    public void excluir(Long id) {
        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipamento não encontrado"));

        if (equipamento.getStatus() == StatusEquipamento.EM_USO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não pode remover equipamento em uso!");
        }

        equipamentoRepository.delete(equipamento);
    }
}