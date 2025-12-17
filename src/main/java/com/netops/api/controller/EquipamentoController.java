package com.netops.api.controller;

import com.netops.api.model.Equipamento;
import com.netops.api.model.StatusEquipamento;
import com.netops.api.service.EquipamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoService service;

    @GetMapping
    public List<Equipamento> listar() {
        return service.listarTudo();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipamento criar(@RequestBody @Valid Equipamento equipamento) {
        return service.salvar(equipamento);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.excluir(id);
    }

    @PutMapping("/{idEquipamento}/vinculo/{idTecnico}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincular(@PathVariable Long idEquipamento, @PathVariable Long idTecnico) {
        service.vincularTecnico(idEquipamento, idTecnico);
    }

    @PutMapping("/{id}/devolucao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void devolucao(@PathVariable Long id, @RequestParam StatusEquipamento status) {
        service.devolverEquipamento(id, status);
    }

}