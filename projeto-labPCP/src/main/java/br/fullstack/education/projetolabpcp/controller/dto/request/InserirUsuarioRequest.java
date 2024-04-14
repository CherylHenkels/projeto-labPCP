package br.fullstack.education.projetolabpcp.controller.dto.request;


public record InserirUsuarioRequest(
        String usuario,
        String senha,
        String nomePapel
) {
}
