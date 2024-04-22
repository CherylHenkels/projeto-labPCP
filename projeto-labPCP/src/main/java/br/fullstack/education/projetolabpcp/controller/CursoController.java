package br.fullstack.education.projetolabpcp.controller;


import br.fullstack.education.projetolabpcp.controller.dto.request.CursoRequest;
import br.fullstack.education.projetolabpcp.controller.dto.request.DocenteRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.CursoResponse;
import br.fullstack.education.projetolabpcp.controller.dto.response.DocenteResponse;
import br.fullstack.education.projetolabpcp.controller.dto.response.MateriaResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.CursoEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.MateriaEntity;
import br.fullstack.education.projetolabpcp.infra.utils.JsonUtil;
import br.fullstack.education.projetolabpcp.service.CursoService;
import br.fullstack.education.projetolabpcp.service.MateriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("cursos")

public class CursoController {

    private final CursoService service;
    private final MateriaService materiaService;


    @GetMapping
    public ResponseEntity<List<CursoResponse>> buscarTodos() {
        log.info("GET /cursos -> Início");
        List<CursoResponse> cursos = service.buscarTodos();
        log.info("GET /cursos -> Encontrados {} registros", cursos.size());
        log.info("GET /cursos -> 200 OK");
        log.debug("GET /cursos -> Response Body:\n{}\n", JsonUtil.objetoParaJson(cursos));
        return ResponseEntity.status(HttpStatus.OK).body(cursos);
    }

    @GetMapping("{id_curso}/materias")
    public ResponseEntity<List<MateriaResponse>> buscarMateriasPorCursoId(@PathVariable("id_curso") Long idCurso) {
        log.info("GET /cursos/{id_curso}/materias -> Início");
        List<MateriaResponse> materias = materiaService.buscarPorCursoId(idCurso);
        log.info("GET /cursos/{id_curso}/materias -> Encontrados {} registros", materias.size());
        log.info("GET /cursos/{id_curso}/materias -> 200 OK");
        log.debug("GET /cursos/{id_curso}/materias -> Response Body:\n{}\n", JsonUtil.objetoParaJson(materias));
        return ResponseEntity.status(HttpStatus.OK).body(materias);
    }


    @GetMapping("{id}")
    public ResponseEntity<CursoResponse> buscarPorId(@PathVariable Long id) {
        log.info("GET /cursos/{} -> Início" , id );
        CursoResponse curso = service.buscarPorId(id);
        log.info("GET /cursos/{} -> Curso encontrado", id);
        log.info("GET /cursos/{} -> 200 OK", id);
        log.debug("GET /cursos/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(curso));
        return ResponseEntity.status(HttpStatus.OK).body(curso);
    }


    @PostMapping
    public ResponseEntity<CursoResponse> criarCurso(@RequestBody CursoRequest cursoRequest) {
        log.info("POST /cursos -> Início");
        CursoResponse curso = service.criar(cursoRequest);
        log.info("POST /cursos -> Curso criado com sucesso");
        log.info("POST /cursos -> 201 CREATED");
        log.debug("POST /cursos -> Response Body:\n{}\n", JsonUtil.objetoParaJson(curso));
        return ResponseEntity.status(HttpStatus.CREATED).body(curso);
    }



    @PutMapping("{id}")
    public ResponseEntity<CursoResponse> alterarCurso(@PathVariable Long id, @RequestBody CursoRequest cursoRequest) {
        log.info("PUT /cursos/{} -> Início", id);
        CursoResponse curso = service.alterar(id, cursoRequest);
        log.info("PUT /cursos/{} -> Curso atualizado com sucesso", id);
        log.info("PUT /cursos/{} -> 200 OK", id);
        log.debug("PUT /cursos/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(curso));
        return ResponseEntity.status(HttpStatus.OK).body(curso);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarCurso(@PathVariable Long id) {
        log.info("DELETE /cursos/{} -> Início", id);
        service.excluir(id);
        log.info("DELETE /cursos/{} -> Curso excluído com sucesso", id);
        log.info("DELETE /cursos/{} -> 204 NO CONTENT", id);
        return ResponseEntity.noContent().build();
    }


}
