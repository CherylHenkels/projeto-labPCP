package br.fullstack.education.projetolabpcp.datasource.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "turmas")
public class TurmaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_docente", nullable = false)
    private DocenteEntity docente;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private CursoEntity curso;
}
