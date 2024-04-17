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
@Table(name = "notas")
public class NotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_aluno", nullable = false)
    private AlunoEntity aluno;

    @ManyToOne
    @JoinColumn(name = "id_docente", nullable = false)
    private DocenteEntity docente;

    @ManyToOne
    @JoinColumn(name = "id_materia", nullable = false)
    private MateriaEntity materia;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate data = LocalDate.now();

    @Column(nullable = false)
    private Double valor;
}
