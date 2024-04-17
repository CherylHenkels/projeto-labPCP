package br.fullstack.education.projetolabpcp.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public record NotaResponse(Long id, Double valor,
                           @JsonSerialize(using = LocalDateSerializer.class)
                              @JsonDeserialize(using = LocalDateDeserializer.class)
                              @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") LocalDate data,
                           Long id_aluno, Long id_docente, Long id_materia) {
}
