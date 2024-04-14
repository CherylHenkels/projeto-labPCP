package br.fullstack.education.projetolabpcp.controller.dto.request;


import org.antlr.v4.runtime.misc.NotNull;

public record InserirUsuarioRequest(
        String nomeUsuario,
        String senha,
        String nomePerfil
) {
}
