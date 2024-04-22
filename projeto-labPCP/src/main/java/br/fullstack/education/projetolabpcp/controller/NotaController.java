package br.fullstack.education.projetolabpcp.controller;


import br.fullstack.education.projetolabpcp.controller.dto.request.NotaRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.NotaResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.NotaEntity;
import br.fullstack.education.projetolabpcp.infra.utils.JsonUtil;
import br.fullstack.education.projetolabpcp.service.NotaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("notas")

public class NotaController {

    private final NotaService service;


    @GetMapping
    public ResponseEntity<List<NotaResponse>> buscarTodos() {
        log.info("GET /notas -> Início");
        List<NotaResponse> notas = service.buscarTodos();
        log.info("GET /notas -> Encontrados {} registros", notas.size());
        log.info("GET /notas -> 200 OK");
        log.debug("GET /notas -> Response Body:\n{}\n", JsonUtil.objetoParaJson(notas));
        return ResponseEntity.status(HttpStatus.OK).body(notas);
    }


    @GetMapping("{id}")
    public ResponseEntity<NotaResponse> buscarPorId(@PathVariable Long id) {
        log.info("GET /notas/{} -> Início" , id );
        NotaResponse nota = service.buscarPorId(id);
        log.info("GET /notas/{} -> Nota encontrada", id);
        log.info("GET /notas/{} -> 200 OK", id);
        log.debug("GET /notas/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(nota));
        return ResponseEntity.status(HttpStatus.OK).body(nota);
    }


    @PostMapping
    public ResponseEntity<NotaResponse> criarNota(@RequestBody NotaRequest notaRequest) {
        log.info("POST /notas -> Início");
        NotaResponse nota = service.criar(notaRequest);
        log.info("POST /notas -> Nota criada com sucesso");
        log.info("POST /notas -> 201 CREATED");
        log.debug("POST /notas -> Response Body:\n{}\n", JsonUtil.objetoParaJson(nota));
        return ResponseEntity.status(HttpStatus.CREATED).body(nota);
    }


    @PutMapping("{id}")
    public ResponseEntity<NotaResponse> alterarNota(@PathVariable Long id, @RequestBody NotaRequest notaRequest) {
        log.info("PUT /notas/{} -> Início", id);
        NotaResponse nota = service.alterar(id, notaRequest);
        log.info("PUT /notas/{} -> Nota atualizada com sucesso", id);
        log.info("PUT /notas/{} -> 200 OK", id);
        log.debug("PUT /notas/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(nota));
        return ResponseEntity.status(HttpStatus.OK).body(nota);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarNota(@PathVariable Long id) {
        log.info("DELETE /notas/{} -> Início", id);
        service.excluir(id);
        log.info("DELETE /notas/{} -> Nota excluída com sucesso", id);
        log.info("DELETE /notas/{} -> 204 NO CONTENT", id);
        return ResponseEntity.noContent().build();
    }

}
