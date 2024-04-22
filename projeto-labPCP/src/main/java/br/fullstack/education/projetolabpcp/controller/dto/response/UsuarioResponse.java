package br.fullstack.education.projetolabpcp.controller.dto.response;

public record UsuarioResponse(Long id, String usuario,
                              String senha, String nomePapel) {
}
