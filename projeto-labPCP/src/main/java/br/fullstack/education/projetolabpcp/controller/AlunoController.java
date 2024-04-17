package br.fullstack.education.projetolabpcp.controller;


import br.fullstack.education.projetolabpcp.controller.dto.request.AlunoRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.AlunoResponse;
import br.fullstack.education.projetolabpcp.controller.dto.response.NotaResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.NotaEntity;
import br.fullstack.education.projetolabpcp.service.AlunoService;
import br.fullstack.education.projetolabpcp.datasource.entity.AlunoEntity;
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
@RequestMapping("alunos")

public class AlunoController {

    private final AlunoService service;
    private final NotaService notaService;


    @GetMapping
    public ResponseEntity<List<AlunoResponse>> buscarTodos() {
        log.info("GET /alunos -> Início");
        List<AlunoResponse> alunos = service.buscarTodos();
        log.info("GET /alunos -> Encontrados {} registros", alunos.size());
        log.info("GET /alunos -> 200 OK");
        log.debug("GET /alunos -> Response Body:\n{}\n", JsonUtil.objetoParaJson(alunos));
        return ResponseEntity.status(HttpStatus.OK).body(alunos);
    }


    @GetMapping("{id}")
    public ResponseEntity<AlunoResponse> buscarPorId(@PathVariable Long id) {
        log.info("GET /alunos/{} -> Início" , id );
        AlunoResponse aluno = service.buscarPorId(id);
        log.info("GET /alunos/{} -> Aluno encontrado", id);
        log.info("GET /alunos/{} -> 200 OK", id);
        log.debug("GET /alunos/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(aluno));
        return ResponseEntity.status(HttpStatus.OK).body(aluno);
    }

    @GetMapping({"{id_aluno}/notas"})
    public ResponseEntity<List<NotaResponse>> buscarNotasPorAlunoId(@PathVariable("id_aluno") Long idAluno) {
        log.info("GET /alunos/{id_aluno}/notas -> Início");
        List<NotaResponse> notas = notaService.buscarPorAlunoId(idAluno);
        log.info("GET /alunos/{id_aluno}/notas -> Encontrados {} registros", notas.size());
        log.info("GET /alunos/{id_aluno}/notas-> 200 OK");
        log.debug("GET /alunos/{id_aluno}/notas -> Response Body:\n{}\n", JsonUtil.objetoParaJson(notas));
        return ResponseEntity.status(HttpStatus.OK).body(notas);
    }


    @PostMapping
    public ResponseEntity<AlunoResponse> criarAluno(@RequestHeader(name = "Authorization") String token,
                                                  @RequestBody AlunoRequest alunoRequest) {
        log.info("POST /alunos -> Início");
        AlunoResponse aluno = service.criar(alunoRequest, token.substring(7));
        log.info("POST /alunos -> Aluno criado com sucesso.");
        log.info("POST /alunos -> 201 CREATED");
        log.debug("POST /alunos -> Response Body:\n{}\n", JsonUtil.objetoParaJson(aluno));
        return ResponseEntity.status(HttpStatus.CREATED).body(aluno);
    }


    @PutMapping("{id}")
    public ResponseEntity<AlunoResponse> alterarAluno(@PathVariable Long id, @RequestBody AlunoRequest alunoRequest) {
        log.info("PUT /alunos/{} -> Início", id);
        AlunoResponse aluno = service.alterar(id, alunoRequest);
        log.info("PUT /alunos/{} -> Aluno atualizado com sucesso", id);
        log.info("PUT /alunos/{} -> 200 OK", id);
        log.debug("PUT /alunos/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(aluno));
        return ResponseEntity.status(HttpStatus.OK).body(aluno);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        log.info("DELETE /alunos/{} -> Início", id);
        service.excluir(id);
        log.info("DELETE /alunos/{} -> Aluno excluído com sucesso", id);
        log.info("DELETE /alunos/{} -> 204 NO CONTENT", id);
        return ResponseEntity.noContent().build();
    }

}
