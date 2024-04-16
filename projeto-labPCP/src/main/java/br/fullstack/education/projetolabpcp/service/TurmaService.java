package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.datasource.entity.TurmaEntity;

import java.util.List;

public interface TurmaService {

    List<TurmaEntity> buscarTodos();

    TurmaEntity buscarPorId(Long id);

    TurmaEntity criar(TurmaEntity entity, String token);

    TurmaEntity alterar(Long id, TurmaEntity entity);

    void excluir(Long id);

}
