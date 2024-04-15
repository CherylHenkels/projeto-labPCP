package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.datasource.entity.AlunoEntity;

import java.util.List;

public interface AlunoService {

    List<AlunoEntity> buscarTodos();

    AlunoEntity buscarPorId(Long id);

    AlunoEntity criar(AlunoEntity entity);

    AlunoEntity alterar(Long id, AlunoEntity entity);

    void excluir(Long id);

}
