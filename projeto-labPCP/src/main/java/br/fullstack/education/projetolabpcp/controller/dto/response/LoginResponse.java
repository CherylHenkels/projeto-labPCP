package br.fullstack.education.projetolabpcp.controller.dto.response;

public record LoginResponse (String valorJWT, long tempoExpiracao) {
}
