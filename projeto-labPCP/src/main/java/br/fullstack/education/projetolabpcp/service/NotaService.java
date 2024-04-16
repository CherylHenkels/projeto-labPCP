package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.datasource.entity.NotaEntity;

import java.util.List;

public interface NotaService {

    List<NotaEntity> buscarTodos();

    NotaEntity buscarPorId(Long id);

    NotaEntity criar(NotaEntity entity, String token);

    NotaEntity alterar(Long id, NotaEntity entity);

    void excluir(Long id);

    List<NotaEntity> buscarPorAlunoId(Long idAluno);
}
