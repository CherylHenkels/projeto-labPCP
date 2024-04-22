package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.TurmaRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.TurmaResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.TurmaEntity;

import java.util.List;

public interface TurmaService {

    List<TurmaResponse> buscarTodos();

    TurmaResponse buscarPorId(Long id);

    TurmaResponse criar(TurmaRequest turmaRequest);

    TurmaResponse alterar(Long id, TurmaRequest turmaRequest);

    void excluir(Long id);

}
