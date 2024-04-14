package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.InserirTesteRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.TesteResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.TesteEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.UsuarioEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.TesteRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TesteService {

    private final TesteRepository testeRepository;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;


    public List<TesteResponse> retornaTestes(String token) {

        Long usuarioId = Long.valueOf( // tranforma o valor do campo "sub" em Long
                tokenService.buscaCampo(token, "sub")
        );



        List<TesteEntity> listaTestes = testeRepository.findAllByUsuarioId(usuarioId);

        return listaTestes.stream().map( // mapear a lista de TesteEntity para uma lista de TesteResponse
                // para cada Item da lista TesteEntity será criada uma nova TesteResponse
                t -> new TesteResponse(t.getId(), t.getTitulo())
        ).toList(); // cria uma lista de TesteResponse


    }

    public TesteResponse salvaTeste(
            InserirTesteRequest incluiTesteRequest,
            String token
    ) {


        Long usuarioId = Long.valueOf( // tranforma o valor do campo "sub" em Long
                tokenService.buscaCampo(token, "sub")
        );

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        TesteEntity testeEntity = new TesteEntity();
        testeEntity.setUsuario(usuario);
        testeEntity.setTitulo(incluiTesteRequest.titulo());

        TesteEntity testeSalvo = testeRepository.save(testeEntity);

        return new TesteResponse(testeSalvo.getId(),
                testeSalvo.getTitulo()
        );
    }
}
