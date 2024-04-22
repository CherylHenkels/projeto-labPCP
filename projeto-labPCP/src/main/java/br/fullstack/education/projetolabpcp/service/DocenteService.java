package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.DocenteRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.DocenteResponse;

import java.util.List;

public interface DocenteService {

    List<DocenteResponse> buscarTodos();

    DocenteResponse buscarPorId(Long id);

    DocenteResponse criar(DocenteRequest docenteRequest);

    DocenteResponse alterar(Long id, DocenteRequest docenteRequest);

    void excluir(Long id);

}
