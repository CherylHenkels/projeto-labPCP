package br.fullstack.education.projetolabpcp.controller.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MateriaRequest {
    private String nome;
    private Long id_curso;
}
