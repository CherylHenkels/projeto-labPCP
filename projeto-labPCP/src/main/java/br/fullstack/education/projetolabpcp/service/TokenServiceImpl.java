package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.LoginRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.LoginResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.UsuarioEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.UsuarioRepository;
import br.fullstack.education.projetolabpcp.infra.exception.InvalidCredentialsException;
import br.fullstack.education.projetolabpcp.infra.exception.InvalidRequestException;
import br.fullstack.education.projetolabpcp.infra.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final BCryptPasswordEncoder bCryptEncoder; // decifrar senhas
    private final JwtEncoder jwtEncoder; // codificar um JWT
    private final JwtDecoder jwtDencoder; // decofica um JWT
    private final UsuarioRepository usuarioRepository;

    private static long TEMPO_EXPIRACAO = 360000L; //contante de tempo de expiração em segundos (36000=10horas)

    @Override
    public LoginResponse gerarToken(
            @RequestBody LoginRequest loginRequest
    ){

        if (loginRequest.usuario() == null || loginRequest.usuario().trim().isEmpty()) {
            log.error("400 BAD REQUEST -> Nome do usuário é obrigatório");
            throw new InvalidRequestException("Nome do usuário é obrigatório");
        }
        if (loginRequest.senha() == null || loginRequest.senha().trim().isEmpty()) {
            log.error("400 BAD REQUEST -> Senha é obrigatória");
            throw new InvalidRequestException("Senha é obrigatória");
        }

        UsuarioEntity usuarioEntity = usuarioRepository
                .findByNomeUsuario(loginRequest.usuario()) // busca dados de usuario pelo nome do usuario
                .orElseThrow(                                  // caso usuario não exista, gera um erro
                        ()->{
                            log.error("404 NOT FOUND -> Usuário não existe");
                            return new NotFoundException("Usuário não existe");
                        }
                );

        if(!usuarioEntity.senhaValida(loginRequest, bCryptEncoder)){ // valida se a senha recebida é a mesma que foi salva com BCrypt
            log.error("401 UNAUTHORIZED -> Senha incorreta");                      // caso senha não bata, gera um erro
            throw new InvalidCredentialsException("Senha incorreta");
        }

        Instant now = Instant.now();

        String scope = usuarioEntity.getPapel().getNome();

        JwtClaimsSet claims = JwtClaimsSet.builder() // Conjunto de campos do JWT, incluindo os campos pré-definidos e campos customizados
                .issuer("projeto1") // autor do token
                .issuedAt(now)      // momento da criação do token
                .expiresAt(now.plusSeconds(TEMPO_EXPIRACAO)) // tempo de expiração
                .subject(usuarioEntity.getId().toString())   // sujeito do token ou dono do token
                .claim("scope", scope) // campo customizado, chamado scope que será adicionado ao token, alem dos campos anteriores
                .build(); // constroi o Objeto de JwtClaimsSet

        var valorJWT = jwtEncoder.encode(
                        JwtEncoderParameters.from(claims) // parametros para encode do token
                ) // token foi criado, porém está em uma classe que não tem o token puro, ele o token e várias coisas a mais
                .getTokenValue(); // pegar o valor do token em si

        return new LoginResponse(valorJWT, TEMPO_EXPIRACAO); // corpo de resposta é um objeto de LoginResponse


    }


    @Override
    public String buscaCampo(String token, String claim) {
        return jwtDencoder
                .decode(token) // decifra o token
                .getClaims() // busca um campo especifico do token
                .get(claim)    // definindo o campo a ser buscado
                .toString(); // transforma a resposta em string
    }
}
