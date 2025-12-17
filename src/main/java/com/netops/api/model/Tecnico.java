package com.netops.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "tecnicos")
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tecID")
    private Long id;

    private String nome;

    private String telefone;

    @OneToMany(mappedBy = "tecnico")
    private List<Equipamento> equipamentos;

}
