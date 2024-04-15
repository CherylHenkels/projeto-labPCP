package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.datasource.entity.DocenteEntity;

import java.util.List;

public interface DocenteService {

    List<DocenteEntity> buscarTodos();

    DocenteEntity buscarPorId(Long id);

    DocenteEntity criar(DocenteEntity entity, String token);

    DocenteEntity alterar(Long id, DocenteEntity entity);

    void excluir(Long id);

}
