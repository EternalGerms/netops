package com.netops.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tecnicos")
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tecID")
    private Long id;

    private String nome;

    private String telefone;

    @OneToMany(mappedBy = "tecnico", fetch = FetchType.LAZY)
    private List<Equipamento> equipamentos;

}
