package br.fullstack.education.projetolabpcp.controller.dto.request;


public record UsuarioRequest(
        String usuario,
        String senha,
        String nomePapel
) {
}
