package br.fullstack.education.projetolabpcp.controller;


import br.fullstack.education.projetolabpcp.controller.dto.request.MateriaRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.MateriaResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.MateriaEntity;
import br.fullstack.education.projetolabpcp.infra.utils.JsonUtil;
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
@RequestMapping("materias")

public class MateriaController {

    private final MateriaService service;


    @GetMapping
    public ResponseEntity<List<MateriaResponse>> buscarTodos() {
        log.info("GET /materias -> Início");
        List<MateriaResponse> materias = service.buscarTodos();
        log.info("GET /materias -> Encontrados {} registros", materias.size());
        log.info("GET /materias -> 200 OK");
        log.debug("GET /materias -> Response Body:\n{}\n", JsonUtil.objetoParaJson(materias));
        return ResponseEntity.status(HttpStatus.OK).body(materias);
    }


    @GetMapping("{id}")
    public ResponseEntity<MateriaResponse> buscarPorId(@PathVariable Long id) {
        log.info("GET /materias/{} -> Início" , id );
        MateriaResponse materia = service.buscarPorId(id);
        log.info("GET /materias/{} -> Matéria encontrada", id);
        log.info("GET /materias/{} -> 200 OK", id);
        log.debug("GET /materias/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(materia));
        return ResponseEntity.status(HttpStatus.OK).body(materia);
    }

//    @GetMapping("/por-curso/{id_curso}")
//    public ResponseEntity<List<MateriaEntity>> buscarPorCursoId(@PathVariable("id_curso") Long idCurso) {
//        log.info("GET /materias/{} -> Início" , idCurso );
//        List<MateriaEntity> materias = service.buscarPorCursoId(idCurso);
//        log.info("GET /materias/curso -> Encontrados {} registros", materias.size());
//        log.info("GET /materias/curso -> 200 OK");
//        log.debug("GET /materias/curso -> Response Body:\n{}\n", JsonUtil.objetoParaJson(materias));
//        return ResponseEntity.status(HttpStatus.OK).body(materias);
//    }

    @PostMapping
    public ResponseEntity<MateriaResponse> criarMateria(@RequestHeader(name = "Authorization") String token,
                                                      @RequestBody MateriaRequest materiaRequest) {
        log.info("POST /materias -> inicio");
        MateriaResponse materia = service.criar(materiaRequest, token.substring(7));
        log.info("POST /materias -> Matéria criada com sucesso");
        log.info("POST /materias -> 201 CREATED");
        log.debug("POST /materias -> Response Body:\n{}\n", JsonUtil.objetoParaJson(materia));
        return ResponseEntity.status(HttpStatus.CREATED).body(materia);
    }


    @PutMapping("{id}")
    public ResponseEntity<MateriaResponse> alterarMateria(@PathVariable Long id, @RequestBody MateriaRequest materiaRequest) {
        log.info("PUT /materias/{} -> Início", id);
        MateriaResponse materia = service.alterar(id, materiaRequest);
        log.info("PUT /materias/{} -> Matéria atualizada com sucesso", id);
        log.info("PUT /materias/{} -> 200 OK", id);
        log.debug("PUT /materias/{} -> Response Body:\n{}\n", id, JsonUtil.objetoParaJson(materia));
        return ResponseEntity.status(HttpStatus.OK).body(materia);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarMateria(@PathVariable Long id) {
        log.info("DELETE /materias/{} -> Início", id);
        service.excluir(id);
        log.info("DELETE /materias/{} -> Matéria excluída com sucesso", id);
        log.info("DELETE /materias/{} -> 204 NO CONTENT", id);
        return ResponseEntity.noContent().build();
    }

}
