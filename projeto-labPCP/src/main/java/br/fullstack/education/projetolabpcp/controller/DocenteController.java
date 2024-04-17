package br.fullstack.education.projetolabpcp.controller;


import br.fullstack.education.projetolabpcp.controller.dto.request.DocenteRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.DocenteResponse;
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
    public ResponseEntity<List<DocenteResponse>> buscarTodos() {
        log.info("GET /docentes -> Início");
        List<DocenteResponse> docentes = service.buscarTodos();
        log.info("GET /docentes -> Encontrados {} registros", docentes.size());
        log.info("GET /docentes -> 200 OK");
        log.debug("GET /docentes -> Response Body:\n{}\n", JsonUtil.objetoParaJson(docentes));
        return ResponseEntity.status(HttpStatus.OK).body(docentes);
    }


    @GetMapping("{id}")
    public ResponseEntity<DocenteResponse> buscarPorId(@PathVariable Long id) {
        log.info("GET /docentes/{} -> Início" , id );
        DocenteResponse docente = service.buscarPorId(id);
        log.info("GET /docentes/{} -> Docente encontrado", id);
        log.info("GET /docentes/{} -> 200 OK", id);
        log.debug("GET /docentes/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(docente));
        return ResponseEntity.status(HttpStatus.OK).body(docente);
    }


    @PostMapping
    public ResponseEntity<DocenteResponse> criarDocente(@RequestHeader(name = "Authorization") String token,
                                                      @RequestBody DocenteRequest docenteRequest) {
        log.info("POST /docentes -> Início");
        DocenteResponse docente = service.criar(docenteRequest, token.substring(7));
        log.info("POST /docentes -> Docente criado com sucesso");
        log.info("POST /docentes -> 201 CREATED");
        log.debug("POST /docentes -> Response Body:\n{}\n", JsonUtil.objetoParaJson(docente));
        return ResponseEntity.status(HttpStatus.CREATED).body(docente);
    }


    @PutMapping("{id}")
    public ResponseEntity<DocenteResponse> alterarDocente(@PathVariable Long id, @RequestBody DocenteRequest docenteRequest) {
        log.info("PUT /docentes/{} -> Início", id);
        DocenteResponse docente = service.alterar(id, docenteRequest);
        log.info("PUT /docentes/{} -> Docente atualizado com sucesso", id);
        log.info("PUT /docentes/{} -> 200 OK", id);
        log.debug("PUT /docentes/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(docente));
        return ResponseEntity.status(HttpStatus.OK).body(docente);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarDocente(@PathVariable Long id) {
        log.info("DELETE /docentes/{} -> Início", id);
        service.excluir(id);
        log.info("DELETE /docentes/{} -> Docente excluído com sucesso", id);
        log.info("DELETE /docentes/{} -> 204 NO CONTENT", id);
        return ResponseEntity.noContent().build();
    }

}
