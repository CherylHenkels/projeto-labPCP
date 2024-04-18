package br.fullstack.education.projetolabpcp.controller;

import br.fullstack.education.projetolabpcp.controller.dto.request.InserirUsuarioRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.InserirUsuarioResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.UsuarioEntity;
import br.fullstack.education.projetolabpcp.infra.utils.JsonUtil;
import br.fullstack.education.projetolabpcp.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j

public class CadastroController {

    private final UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public ResponseEntity<InserirUsuarioResponse> novoUsuario(
            @RequestHeader(name = "Authorization") String token,
             @RequestBody InserirUsuarioRequest inserirUsuarioRequest
    ) {
        log.info("POST /cadastro -> Início");
        InserirUsuarioResponse usuario = usuarioService.cadastraNovoUsuario(inserirUsuarioRequest, token.substring(7));
        log.info("POST /cadastro -> Usuário cadastrado com sucesso");
        log.info("POST /cadastro -> 201 CREATED");
        log.debug("POST /cadastro -> Response Body:\n{}\n", JsonUtil.objetoParaJson(usuario));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

}