package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.InserirUsuarioRequest;
import br.fullstack.education.projetolabpcp.datasource.entity.UsuarioEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.UsuarioRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.PapelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final BCryptPasswordEncoder bCryptEncoder;
    private final UsuarioRepository usuarioRepository;
    private final PapelRepository papelRepository;

    public UsuarioEntity cadastraNovoUsuario(
            @RequestBody InserirUsuarioRequest inserirUsuarioRequest
    ) {
        boolean usuarioExiste = usuarioRepository
                .findByNomeUsuario(inserirUsuarioRequest.usuario())
                .isPresent(); // retorna um true se a entidade procurada existir, caso o contrário, retorna false

        if (usuarioExiste) {
            throw new RuntimeException("Usuario já existe");
        }

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNomeUsuario(inserirUsuarioRequest.usuario());
        usuario.setSenha(
                bCryptEncoder.encode(inserirUsuarioRequest.senha()) // codificar a senha
        );
        usuario.setPapel(
                papelRepository.findByNome(inserirUsuarioRequest.nomePapel())
                        .orElseThrow(() -> new RuntimeException("Papel inválido ou inexistente"))
        );

        usuarioRepository.save(usuario);
        return usuario;
    }
}
