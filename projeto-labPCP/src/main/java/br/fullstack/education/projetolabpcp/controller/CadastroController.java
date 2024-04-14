package br.fullstack.education.projetolabpcp.controller;

import br.fullstack.education.projetolabpcp.controller.dto.request.InserirUsuarioRequest;
import br.fullstack.education.projetolabpcp.datasource.entity.UsuarioEntity;
import br.fullstack.education.projetolabpcp.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j

public class CadastroController {
    private final UsuarioService usuarioService;

    @PostMapping("/cadastro")
    public ResponseEntity<String> novoUsuario(
            @Validated @RequestBody InserirUsuarioRequest inserirUsuarioRequest
    ) {

        log.info("POST /cadastro");
        UsuarioEntity usuario = usuarioService.cadastraNovoUsuario(inserirUsuarioRequest);

        log.info("POST /cadastro -> 201 CREATED - Usuário cadastrado com sucesso");
        return ResponseEntity.ok("Usuário cadastrado com sucesso");
    }

}