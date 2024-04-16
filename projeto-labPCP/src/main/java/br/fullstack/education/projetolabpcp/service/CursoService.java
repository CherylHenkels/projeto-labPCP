package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.datasource.entity.CursoEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.MateriaEntity;

import java.util.List;

public interface CursoService {

    List<CursoEntity> buscarTodos();

    CursoEntity buscarPorId(Long id);

    CursoEntity criar(CursoEntity entity, String token);

    CursoEntity alterar(Long id, CursoEntity entity);

    void excluir(Long id);


}
