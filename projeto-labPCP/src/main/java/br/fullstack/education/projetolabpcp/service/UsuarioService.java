package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.UsuarioRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.UsuarioResponse;



public interface UsuarioService {

    UsuarioResponse cadastraNovoUsuario(UsuarioRequest usuarioRequest, String token);

}
