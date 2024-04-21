package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.InserirUsuarioRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.InserirUsuarioResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.UsuarioEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.PapelRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.UsuarioRepository;
import br.fullstack.education.projetolabpcp.infra.exception.InvalidCredentialsException;
import br.fullstack.education.projetolabpcp.infra.exception.InvalidRequestException;
import br.fullstack.education.projetolabpcp.infra.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    private final BCryptPasswordEncoder bCryptEncoder;
    private final UsuarioRepository usuarioRepository;
    private final PapelRepository papelRepository;
    private final TokenService tokenService;

    @Override
    public InserirUsuarioResponse cadastraNovoUsuario(
            @RequestBody InserirUsuarioRequest inserirUsuarioRequest,
            String token
    ) {
        //Valida se perfil tem acesso a funcionalidade
        String nomePerfil =  tokenService.buscaCampo(token, "scope");
        System.out.println(nomePerfil);
        if (Objects.equals(nomePerfil, "PEDAGOGICO") || Objects.equals(nomePerfil, "RECRUITER")
        || Objects.equals(nomePerfil, "PROFESSOR") || Objects.equals(nomePerfil, "ALUNO")){
            log.error("401 UNAUTHORIZED -> {} não tem acesso a essa funcionalidade", nomePerfil);
            throw new InvalidCredentialsException(nomePerfil + " não tem acesso a essa funcionalidade");
        }

        if (inserirUsuarioRequest.usuario() == null || inserirUsuarioRequest.usuario().trim().isEmpty()) {
            log.error("400 BAD REQUEST -> Nome do usuário é obrigatório");
            throw new InvalidRequestException("Nome do usuário é obrigatório");
        }
        if (inserirUsuarioRequest.senha() == null || inserirUsuarioRequest.senha().trim().isEmpty()) {
            log.error("400 BAD REQUEST -> Senha é obrigatória");
            throw new InvalidRequestException("Senha é obrigatória");
        }

        boolean usuarioExiste = usuarioRepository
                .findByNomeUsuario(inserirUsuarioRequest.usuario())
                .isPresent(); // retorna um true se a entidade procurada existir, caso o contrário, retorna false

        if (usuarioExiste) {
            log.error("409 CONFLICT -> Este usuário já existe");
            throw new UserAlreadyExistsException("Este usuário já existe");
        }

        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNomeUsuario(inserirUsuarioRequest.usuario());
        usuario.setSenha(
                bCryptEncoder.encode(inserirUsuarioRequest.senha()) // codificar a senha
        );
        usuario.setPapel(
                papelRepository.findByNome(inserirUsuarioRequest.nomePapel())
                        .orElseThrow(() -> {
                            log.error("400 BAD REQUEST -> Papel inválido ou inexistente");
                            return new InvalidRequestException("Papel inválido ou inexistente");})
        );

        usuarioRepository.save(usuario);
        return new InserirUsuarioResponse(usuario.getId(), usuario.getNomeUsuario(), inserirUsuarioRequest.senha(), usuario.getPapel().getNome());
    }


}
