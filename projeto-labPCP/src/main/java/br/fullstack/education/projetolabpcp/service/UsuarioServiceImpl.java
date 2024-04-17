package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.InserirUsuarioRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.InserirUsuarioResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.UsuarioEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.PapelRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.UsuarioRepository;
import br.fullstack.education.projetolabpcp.infra.exception.InvalidRequestException;
import br.fullstack.education.projetolabpcp.infra.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final BCryptPasswordEncoder bCryptEncoder;
    private final UsuarioRepository usuarioRepository;
    private final PapelRepository papelRepository;

    @Override
    public InserirUsuarioResponse cadastraNovoUsuario(
            @RequestBody InserirUsuarioRequest inserirUsuarioRequest
    ) {
        if (inserirUsuarioRequest.usuario() == null || inserirUsuarioRequest.usuario().trim().isEmpty()) {
            throw new InvalidRequestException("Nome do usuário é obrigatório");
        }
        if (inserirUsuarioRequest.senha() == null || inserirUsuarioRequest.senha().trim().isEmpty()) {
            throw new InvalidRequestException("Senha é obrigatória");
        }

        boolean usuarioExiste = usuarioRepository
                .findByNomeUsuario(inserirUsuarioRequest.usuario())
                .isPresent(); // retorna um true se a entidade procurada existir, caso o contrário, retorna false

        if (usuarioExiste) {
            throw new UserAlreadyExistsException("Este usuário já existe");
        }

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNomeUsuario(inserirUsuarioRequest.usuario());
        usuario.setSenha(
                bCryptEncoder.encode(inserirUsuarioRequest.senha()) // codificar a senha
        );
        usuario.setPapel(
                papelRepository.findByNome(inserirUsuarioRequest.nomePapel())
                        .orElseThrow(() -> new InvalidRequestException("Papel inválido ou inexistente"))
        );

        usuarioRepository.save(usuario);
        return new InserirUsuarioResponse(usuario.getId(), usuario.getNomeUsuario(), inserirUsuarioRequest.senha(), usuario.getPapel().getNome());
    }


}
