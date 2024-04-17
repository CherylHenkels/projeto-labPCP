package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.MateriaRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.MateriaResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.MateriaEntity;

import java.util.List;

public interface MateriaService {

    List<MateriaResponse> buscarTodos();

    MateriaResponse buscarPorId(Long id);

    MateriaResponse criar(MateriaRequest materiaRequest, String token);

    MateriaResponse alterar(Long id, MateriaRequest materiaRequest);

    void excluir(Long id);

    List<MateriaResponse> buscarPorCursoId(Long idCurso);
}
