package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.datasource.entity.MateriaEntity;

import java.util.List;

public interface MateriaService {

    List<MateriaEntity> buscarTodos();

    MateriaEntity buscarPorId(Long id);

    MateriaEntity criar(MateriaEntity entity, String token);

    MateriaEntity alterar(Long id, MateriaEntity entity);

    void excluir(Long id);

    List<MateriaEntity> buscarPorCursoId(Long idCurso);
}
