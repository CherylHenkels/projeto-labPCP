package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.AlunoRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.AlunoResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.AlunoEntity;

import java.util.List;

public interface AlunoService {

    List<AlunoResponse> buscarTodos();

    AlunoResponse buscarPorId(Long id);

    AlunoResponse criar(AlunoRequest alunoRequest);

    AlunoResponse alterar(Long id, AlunoRequest alunoRequest);

    void excluir(Long id);

}
