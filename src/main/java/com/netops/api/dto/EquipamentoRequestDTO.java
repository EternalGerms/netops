package com.netops.api.dto;

import com.netops.api.model.StatusEquipamento;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EquipamentoRequestDTO {

    @NotBlank(message = "A marca não pode estar em branco")
    private String marca;

    @NotBlank(message = "O modelo é obrigatório")
    private String modelo;

    private String macAddress;

    private String numeroSerie;

    private LocalDate dataAquisicao;

    private StatusEquipamento status; // NOVO, EM_USO, DEFEITO

    private Long tecnicoID;

}
