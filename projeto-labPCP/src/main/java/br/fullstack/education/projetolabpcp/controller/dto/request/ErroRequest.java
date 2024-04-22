package br.fullstack.education.projetolabpcp.controller.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErroRequest {

    private String codigo;
    private String mensagem;

}