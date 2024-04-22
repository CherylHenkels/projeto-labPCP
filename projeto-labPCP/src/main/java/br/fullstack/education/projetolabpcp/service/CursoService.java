package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.CursoRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.CursoResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.CursoEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.MateriaEntity;

import java.util.List;

public interface CursoService {

    List<CursoResponse> buscarTodos();

    CursoResponse buscarPorId(Long id);

    CursoResponse criar(CursoRequest cursoRequest);

    CursoResponse alterar(Long id, CursoRequest cursoRequest);

    void excluir(Long id);


}
