package com.netops.api.controller;

import com.netops.api.model.Equipamento;
import com.netops.api.repository.EquipamentoRepository;
import com.netops.api.service.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoService equipamentoService;

    @Autowired
    private EquipamentoRepository repository;

    @GetMapping
    public List<Equipamento> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Equipamento criar(@RequestBody @Valid Equipamento equipamento) {
        return repository.save(equipamento);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        equipamentoService.excluir(id);
    }
}

