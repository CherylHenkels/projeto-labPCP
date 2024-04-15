package br.fullstack.education.projetolabpcp.controller.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocenteRequest {
    private Long id_usuario;
}
