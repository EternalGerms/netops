package com.netops.api.controller;

import com.netops.api.dto.EquipamentoMapper;
import com.netops.api.dto.EquipamentoRequestDTO;
import com.netops.api.dto.EquipamentoResponseDTO;
import com.netops.api.model.Equipamento;
import com.netops.api.model.StatusEquipamento;
import com.netops.api.service.EquipamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos")
@RequiredArgsConstructor
public class EquipamentoController {

    private final EquipamentoService service;

    @GetMapping
    public List<EquipamentoResponseDTO> listar() {
        return service.listarTudo().stream()
                .map(EquipamentoMapper::toDTO)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipamentoResponseDTO criar(@RequestBody @Valid EquipamentoRequestDTO dto) {

        Equipamento equipamento = EquipamentoMapper.toEntity(dto);
        Equipamento salvo = service.salvar(equipamento);
        return EquipamentoMapper.toDTO(salvo);
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