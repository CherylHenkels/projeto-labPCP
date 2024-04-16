package br.fullstack.education.projetolabpcp.controller;


import br.fullstack.education.projetolabpcp.datasource.entity.TurmaEntity;
import br.fullstack.education.projetolabpcp.infra.utils.JsonUtil;
import br.fullstack.education.projetolabpcp.service.TurmaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("turmas")

public class TurmaController {

    private final TurmaService service;


    @GetMapping
    public ResponseEntity<List<TurmaEntity>> buscarTodos() {
        log.info("GET /turmas -> Início");
        List<TurmaEntity> turmas = service.buscarTodos();
        log.info("GET /turmas -> Encontrados {} registros", turmas.size());
        log.info("GET /turmas -> 200 OK");
        log.debug("GET /turmas -> Response Body:\n{}\n", JsonUtil.objetoParaJson(turmas));
        return ResponseEntity.status(HttpStatus.OK).body(turmas);
    }


    @GetMapping("{id}")
    public ResponseEntity<TurmaEntity> buscarPorId(@PathVariable Long id) {
        log.info("GET /turmas/{} -> Início" , id );
        TurmaEntity turma = service.buscarPorId(id);
        log.info("GET /turmas/{} -> Encontrado", id);
        log.info("GET /turmas/{} -> 200 OK", id);
        log.debug("GET /turmas/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(turma));
        return ResponseEntity.status(HttpStatus.OK).body(turma);
    }


    @PostMapping
    public ResponseEntity<TurmaEntity> criarTurma(@RequestHeader(name = "Authorization") String token,
                                                      @RequestBody TurmaEntity turmaRequest) {
        log.info("POST /turmas");
        TurmaEntity turma = service.criar(turmaRequest, token.substring(7));
        log.info("POST /turmas -> Cadastrado");
        log.info("POST /turmas -> 201 CREATED");
        log.debug("POST /turmas -> Response Body:\n{}\n", JsonUtil.objetoParaJson(turmaRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(turma);
    }


    @PutMapping("{id}")
    public ResponseEntity<TurmaEntity> alterarTurma(@PathVariable Long id, @RequestBody TurmaEntity turmaRequest) {
        log.info("PUT /turmas/{} -> Início", id);
        TurmaEntity turma = service.alterar(id, turmaRequest);
        log.info("PUT /turmas/{} -> Atualizado com sucesso", id);
        log.info("PUT /turmas/{} -> 200 OK", id);
        log.debug("PUT /turmas/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(turmaRequest));
        return ResponseEntity.status(HttpStatus.OK).body(service.alterar(id, turma));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarTurma(@PathVariable Long id) {
        log.info("DELETE /turmas/{}", id);
        service.excluir(id);
        log.info("DELETE /turmas/{} -> Excluído", id);
        log.info("DELETE /turmas/{} -> 204 NO CONTENT", id);
        return ResponseEntity.noContent().build();
    }

}
