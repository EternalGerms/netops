package com.netops.api.service;

import com.netops.api.model.Equipamento;
import com.netops.api.model.StatusEquipamento;
import com.netops.api.model.Tecnico;
import com.netops.api.repository.EquipamentoRepository;
import com.netops.api.repository.TecnicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final TecnicoRepository tecnicoRepository;

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

    @Transactional
    public void vincularTecnico(Long idEquipamento, Long idTecnico) {
        Equipamento equipamento = equipamentoRepository.findById(idEquipamento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipamento não encontrado"));

        Tecnico tecnico = tecnicoRepository.findById(idTecnico)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tecnico não encontrado"));

        if (equipamento.getStatus() == StatusEquipamento.EM_USO || equipamento.getStatus() == StatusEquipamento.BAIXADO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não pode vincular equipamento em uso ou baixado!");
        }
        equipamento.setTecnico(tecnico);
        equipamento.setStatus(StatusEquipamento.EM_USO);
        equipamentoRepository.save(equipamento);
    }

    public void devolverEquipamento(Long idEquipamento, StatusEquipamento statusFinal) {
        Equipamento equipamento = equipamentoRepository.findById(idEquipamento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipamento não encontrado"));


        if (equipamento.getStatus() != StatusEquipamento.EM_USO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Erro: Este equipamento não consta como EM USO. Status atual: " + equipamento.getStatus());
        }

        equipamento.setTecnico(null);
        equipamento.setStatus(statusFinal);
        equipamentoRepository.save(equipamento);
    }

}