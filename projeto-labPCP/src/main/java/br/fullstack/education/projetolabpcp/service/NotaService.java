package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.NotaRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.NotaResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.NotaEntity;

import java.util.List;

public interface NotaService {

    List<NotaResponse> buscarTodos();

    NotaResponse buscarPorId(Long id);

    NotaResponse criar(NotaRequest notaRequest);

    NotaResponse alterar(Long id, NotaRequest entity);

    void excluir(Long id);

    List<NotaResponse> buscarPorAlunoId(Long idAluno);

    Double calcularPontuacaoAluno(Long id);
}
