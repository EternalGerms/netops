package com.netops.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "equipamentos")
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A marca não pode estar em branco")
    @Column(nullable = false)
    private String marca;

    @NotBlank(message = "O modelo é obrigatório")
    @Column(nullable = false)
    private String modelo;

    @Column(unique = true, nullable = false)
    private String macAddress;

    private String numeroSerie;

    private LocalDate dataAquisicao;

    @Enumerated(EnumType.STRING)
    private StatusEquipamento status; // NOVO, EM_USO, DEFEITO
}