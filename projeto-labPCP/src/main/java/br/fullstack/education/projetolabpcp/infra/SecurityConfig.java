package br.fullstack.education.projetolabpcp.infra;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWT;
import org.hibernate.annotations.Immutable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration // indica uma classe de configuração do Spring, essa classe irá conter @Bean
// -> é uma classe criada apenas uma vez dentro do Spring
// Ex: configuração do DB ou segurança é feita através de classes
@EnableWebSecurity // Segurança Web ativada dentro do Spring, traz as configurações padrão do Spring e nos permite alterá-las
// cada configuração padrão se traduz numa classe, e o Spring quarda um objeto dessa classe em memória
@EnableMethodSecurity // Segurança de métodos com o Spring Security
// Complemento ao Web
// permite validações a nível de métodos

public class SecurityConfig {
    @Value("${jwt.public.key}")
    RSAPublicKey key; // é a chave que usamos para encriptar

    @Value("${jwt.private.key}")
    RSAPrivateKey priv; // é a chave que usamos para decriptar

    @Bean // feijão mágico
    // criação de uma classe
    // classe que representa os filtros do spring security

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login").permitAll() // permite os endpoints que tenham o texto que condiz com /login
                        .requestMatchers(HttpMethod.POST, "/cadastro").permitAll()
                        .anyRequest().authenticated() // pede autenticação para todos os endpoints que não foram permitidos
                )
                .csrf(AbstractHttpConfigurer::disable) // desabilita o CSRF, bloqueia clguns tipos de chamadas por padrão
                .oauth2ResourceServer(oath2->
                        oath2.jwt(Customizer.withDefaults()) // Configura o JWT com os padrõe de projeto -> os beans JwtDecoder e JwtEncoder
                ) // configurar esse programa como um Servidor de Recursos OAuth2
                // filtro adicionado a cadeia de filtros do Spring Security
                .sessionManagement(session -> // forma de manter o usuário logado
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // indica que o sistema nunca fica logado, ele depende de um token para validar o acesso
                )
        ;
        return http.build(); // Gera um tipo para o SecurityFilterChain, ele preenche a Chain
    }


    @Bean // Configuração / criação de uma Classe de configuração que o Spring Security vai usar
    JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(this.key).build(); // cria classe decodificadora que usa a chave publica
    }

    @Bean // Configuração / Criação de uma Classe de configuração que o Spring Security irá usar
    JwtEncoder jwtEncoder(){

        JWK jwk = new RSAKey.Builder(this.key)
                .privateKey(this.priv)
                .build(); // crie um objeto JWK com o campo privateKey
        // JWK controla chaves de encriptação

        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>( // lista imutável de JWK
                new JWKSet(jwk)
        );

        return new NimbusJwtEncoder(jwks); // criar um encriptador a partir do JWK criado anteriormente
    }

    @Bean // Configuração para as senhas
    // Vai ser usado para encriptar as senhas antes de salvá-las no banco de dados
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(); // codifica, ou criptografar, senhas com o software BCrypt
    }
}