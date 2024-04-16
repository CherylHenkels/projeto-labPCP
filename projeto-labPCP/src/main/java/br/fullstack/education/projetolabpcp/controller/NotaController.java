package br.fullstack.education.projetolabpcp.controller;


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
    public ResponseEntity<List<NotaEntity>> buscarTodos() {
        log.info("GET /notas -> Início");
        List<NotaEntity> notas = service.buscarTodos();
        log.info("GET /notas -> Encontrados {} registros", notas.size());
        log.info("GET /notas -> 200 OK");
        log.debug("GET /notas -> Response Body:\n{}\n", JsonUtil.objetoParaJson(notas));
        return ResponseEntity.status(HttpStatus.OK).body(notas);
    }


    @GetMapping("{id}")
    public ResponseEntity<NotaEntity> buscarPorId(@PathVariable Long id) {
        log.info("GET /notas/{} -> Início" , id );
        NotaEntity nota = service.buscarPorId(id);
        log.info("GET /notas/{} -> Encontrado", id);
        log.info("GET /notas/{} -> 200 OK", id);
        log.debug("GET /notas/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(nota));
        return ResponseEntity.status(HttpStatus.OK).body(nota);
    }

    @GetMapping("/por-aluno/{id_aluno}")
    public ResponseEntity<List<NotaEntity>> buscarPorAlunoId(@PathVariable("id_aluno") Long idAluno) {
        log.info("GET /notas/por-aluno/{} -> Início" , idAluno );
        List<NotaEntity> notas = service.buscarPorAlunoId(idAluno);
        log.info("GET /notas -> Encontrados {} registros", notas.size());
        log.info("GET /notas -> 200 OK");
        log.debug("GET /notas -> Response Body:\n{}\n", JsonUtil.objetoParaJson(notas));
        return ResponseEntity.status(HttpStatus.OK).body(notas);
    }

    @PostMapping
    public ResponseEntity<NotaEntity> criarNota(@RequestHeader(name = "Authorization") String token,
                                                      @RequestBody NotaEntity notaRequest) {
        log.info("POST /notas");
        NotaEntity nota = service.criar(notaRequest, token.substring(7));
        log.info("POST /notas -> Cadastrado");
        log.info("POST /notas -> 201 CREATED");
        log.debug("POST /notas -> Response Body:\n{}\n", JsonUtil.objetoParaJson(notaRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(nota);
    }


    @PutMapping("{id}")
    public ResponseEntity<NotaEntity> alterarNota(@PathVariable Long id, @RequestBody NotaEntity notaRequest) {
        log.info("PUT /notas/{} -> Início", id);
        NotaEntity nota = service.alterar(id, notaRequest);
        log.info("PUT /notas/{} -> Atualizado com sucesso", id);
        log.info("PUT /notas/{} -> 200 OK", id);
        log.debug("PUT /notas/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(notaRequest));
        return ResponseEntity.status(HttpStatus.OK).body(service.alterar(id, nota));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarNota(@PathVariable Long id) {
        log.info("DELETE /notas/{}", id);
        service.excluir(id);
        log.info("DELETE /notas/{} -> Excluído", id);
        log.info("DELETE /notas/{} -> 204 NO CONTENT", id);
        return ResponseEntity.noContent().build();
    }

}
