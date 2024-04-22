package br.fullstack.education.projetolabpcp.controller;

import br.fullstack.education.projetolabpcp.controller.dto.request.LoginRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.LoginResponse;
import br.fullstack.education.projetolabpcp.datasource.repository.UsuarioRepository;
import br.fullstack.education.projetolabpcp.datasource.entity.UsuarioEntity;
import br.fullstack.education.projetolabpcp.infra.utils.JsonUtil;
import br.fullstack.education.projetolabpcp.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.time.Instant;

@RestController
@RequiredArgsConstructor
@Slf4j

public class TokenController {

    private final TokenService tokenService;
//    private static long TEMPO_EXPIRACAO = 360000L; //contante de tempo de expiração em segundos (10 horas)

    @PostMapping("/login") //post para gerar o token
    public ResponseEntity<LoginResponse> gerarToken(
            @RequestBody LoginRequest loginRequest
    ){
        log.info("POST /login -> Início");
        LoginResponse response = tokenService.gerarToken(loginRequest);
        log.info("POST /login -> Login bem-sucedido");
        log.info("POST /login -> 200 OK");
        log.debug("POST /login ->  Response Body:\n{}\n", JsonUtil.objetoParaJson(response));
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}

