package com.netops.api.dto;

import com.netops.api.model.Equipamento;

public class EquipamentoMapper {

    public static Equipamento toEntity(EquipamentoRequestDTO dto) {
        Equipamento equipamento = new Equipamento();
        equipamento.setMarca(dto.getMarca());
        equipamento.setModelo(dto.getModelo());
        equipamento.setMacAddress(dto.getMacAddress());
        equipamento.setNumeroSerie(dto.getNumeroSerie());
        equipamento.setDataAquisicao(dto.getDataAquisicao());
        equipamento.setStatus(dto.getStatus());
        return equipamento;
    }

    public static EquipamentoResponseDTO toDTO(Equipamento entity) {
        EquipamentoResponseDTO dto = new EquipamentoResponseDTO();
        dto.setId(entity.getId());
        dto.setMarca(entity.getMarca());
        dto.setModelo(entity.getModelo());
        dto.setMacAddress(entity.getMacAddress());
        dto.setNumeroSerie(entity.getNumeroSerie());
        dto.setDataAquisicao(entity.getDataAquisicao());
        dto.setStatus(entity.getStatus());

        if (entity.getTecnico() != null) {
            dto.setTecnicoId(entity.getTecnico().getId());
            dto.setTecnicoNome(entity.getTecnico().getNome());
        }

        return dto;
    }
}