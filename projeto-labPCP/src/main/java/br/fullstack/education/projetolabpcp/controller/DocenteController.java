package br.fullstack.education.projetolabpcp.controller;


import br.fullstack.education.projetolabpcp.datasource.entity.DocenteEntity;
import br.fullstack.education.projetolabpcp.infra.utils.JsonUtil;
import br.fullstack.education.projetolabpcp.service.DocenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("docentes")

public class DocenteController {

    private final DocenteService service;


    @GetMapping
    public ResponseEntity<List<DocenteEntity>> buscarTodos() {
        log.info("GET /docentes -> Início");
        List<DocenteEntity> docentes = service.buscarTodos();
        log.info("GET /docentes -> Encontrados {} registros", docentes.size());
        log.info("GET /docentes -> 200 OK");
        log.debug("GET /docentes -> Response Body:\n{}\n", JsonUtil.objetoParaJson(docentes));
        return ResponseEntity.status(HttpStatus.OK).body(docentes);
    }


    @GetMapping("{id}")
    public ResponseEntity<DocenteEntity> buscarPorId(@PathVariable Long id) {
        log.info("GET /docentes/{} -> Início" , id );
        DocenteEntity docente = service.buscarPorId(id);
        log.info("GET /docentes/{} -> Encontrado", id);
        log.info("GET /docentes/{} -> 200 OK", id);
        log.debug("GET /docentes/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(docente));
        return ResponseEntity.status(HttpStatus.OK).body(docente);
    }


    @PostMapping
    public ResponseEntity<DocenteEntity> criarDocente(@RequestHeader(name = "Authorization") String token,
                                                      @RequestBody DocenteEntity docenteRequest) {
        log.info("POST /docentes");
        DocenteEntity docente = service.criar(docenteRequest, token.substring(7));
        log.info("POST /docentes -> Cadastrado");
        log.info("POST /docentes -> 201 CREATED");
        log.debug("POST /docentes -> Response Body:\n{}\n", JsonUtil.objetoParaJson(docenteRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(docente);
    }


    @PutMapping("{id}")
    public ResponseEntity<DocenteEntity> alterarDocente(@PathVariable Long id, @RequestBody DocenteEntity docenteRequest) {
        log.info("PUT /docentes/{} -> Início", id);
        DocenteEntity docente = service.alterar(id, docenteRequest);
        log.info("PUT /docentes/{} -> Atualizado com sucesso", id);
        log.info("PUT /docentes/{} -> 200 OK", id);
        log.debug("PUT /docentes/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(docenteRequest));
        return ResponseEntity.status(HttpStatus.OK).body(service.alterar(id, docente));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarDocente(@PathVariable Long id) {
        log.info("DELETE /docentes/{}", id);
        service.excluir(id);
        log.info("DELETE /docentes/{} -> Excluído", id);
        log.info("DELETE /docentes/{} -> 204 NO CONTENT", id);
        return ResponseEntity.noContent().build();
    }

}
