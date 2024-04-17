package br.fullstack.education.projetolabpcp.controller.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TurmaRequest {
    private String nome;
    private Long id_docente;
    private Long id_curso;
}
