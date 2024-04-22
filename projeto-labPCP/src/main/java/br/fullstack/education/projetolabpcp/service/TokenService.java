package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.LoginRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.LoginResponse;


public interface TokenService {

    LoginResponse gerarToken(LoginRequest loginRequest);

    String buscaCampo(String token, String sub);
}
