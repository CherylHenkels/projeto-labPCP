package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.InserirUsuarioRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.InserirUsuarioResponse;



public interface UsuarioService {

    InserirUsuarioResponse cadastraNovoUsuario(InserirUsuarioRequest inserirUsuarioRequest, String token);

}
