package br.fullstack.education.projetolabpcp.controller;


import br.fullstack.education.projetolabpcp.controller.dto.request.TurmaRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.TurmaResponse;
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
    public ResponseEntity<List<TurmaResponse>> buscarTodos() {
        log.info("GET /turmas -> Início");
        List<TurmaResponse> turmas = service.buscarTodos();
        log.info("GET /turmas -> Encontrados {} registros", turmas.size());
        log.info("GET /turmas -> 200 OK");
        log.debug("GET /turmas -> Response Body:\n{}\n", JsonUtil.objetoParaJson(turmas));
        return ResponseEntity.status(HttpStatus.OK).body(turmas);
    }


    @GetMapping("{id}")
    public ResponseEntity<TurmaResponse> buscarPorId(@PathVariable Long id) {
        log.info("GET /turmas/{} -> Início" , id );
        TurmaResponse turma = service.buscarPorId(id);
        log.info("GET /turmas/{} -> Turma encontrada", id);
        log.info("GET /turmas/{} -> 200 OK", id);
        log.debug("GET /turmas/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(turma));
        return ResponseEntity.status(HttpStatus.OK).body(turma);
    }


    @PostMapping
    public ResponseEntity<TurmaResponse> criarTurma(@RequestHeader(name = "Authorization") String token,
                                                      @RequestBody TurmaRequest turmaRequest) {
        log.info("POST /turmas -> Início");
        TurmaResponse turma = service.criar(turmaRequest, token.substring(7));
        log.info("POST /turmas -> Turma cadastrada");
        log.info("POST /turmas -> 201 CREATED");
        log.debug("POST /turmas -> Response Body:\n{}\n", JsonUtil.objetoParaJson(turma));
        return ResponseEntity.status(HttpStatus.CREATED).body(turma);
    }


    @PutMapping("{id}")
    public ResponseEntity<TurmaResponse> alterarTurma(@PathVariable Long id, @RequestBody TurmaRequest turmaRequest) {
        log.info("PUT /turmas/{} -> Início", id);
        TurmaResponse turma = service.alterar(id, turmaRequest);
        log.info("PUT /turmas/{} -> Turma atualizada com sucesso", id);
        log.info("PUT /turmas/{} -> 200 OK", id);
        log.debug("PUT /turmas/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(turma));
        return ResponseEntity.status(HttpStatus.OK).body(turma);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarTurma(@PathVariable Long id) {
        log.info("DELETE /turmas/{} -> Início", id);
        service.excluir(id);
        log.info("DELETE /turmas/{} -> Turma excluída com sucesso", id);
        log.info("DELETE /turmas/{} -> 204 NO CONTENT", id);
        return ResponseEntity.noContent().build();
    }

}
