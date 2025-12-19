package com.netops.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "equipamentos")
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipID")
    private Long id;

    private String marca;

    private String modelo;

    private String macAddress;

    private String numeroSerie;

    private LocalDate dataAquisicao;

    @Enumerated(EnumType.STRING)
    private StatusEquipamento status; // NOVO, EM_USO, DEFEITO

    @ManyToOne
    @JoinColumn(name = "tecnicoID", referencedColumnName = "tecID")
    @JsonIgnore
    private Tecnico tecnico;
}