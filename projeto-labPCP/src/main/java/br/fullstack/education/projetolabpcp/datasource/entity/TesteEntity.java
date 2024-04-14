package br.fullstack.education.projetolabpcp.datasource.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "teste")
@Data
public class TesteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    private String titulo;

}
