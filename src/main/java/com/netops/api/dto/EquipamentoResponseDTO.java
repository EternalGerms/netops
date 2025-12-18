package com.netops.api.dto;

import com.netops.api.model.StatusEquipamento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EquipamentoResponseDTO {

    private long id;
    private String marca;
    private String modelo;
    private String macAddress;
    private String numeroSerie;
    private LocalDate dataAquisicao;

    private StatusEquipamento status;

    private Long tecnicoId;
    private String tecnicoNome;

}
