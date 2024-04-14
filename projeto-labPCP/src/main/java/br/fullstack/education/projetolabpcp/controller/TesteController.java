package br.fullstack.education.projetolabpcp.controller;

import br.fullstack.education.projetolabpcp.controller.dto.request.InserirTesteRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.TesteResponse;
import br.fullstack.education.projetolabpcp.service.TesteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("testes")
public class TesteController {

    private final TesteService testeService;

    @GetMapping
    public ResponseEntity<List<TesteResponse>> retornarTestes(
            @RequestHeader(name = "Authorization") String token // Bearer ahsdjkahkjdahjksd
    ){
        return ResponseEntity.ok(
                testeService.retornaTestes(token.substring(7)// remove o Bearer do token
                )
        );
    }

    @PostMapping
    public ResponseEntity<TesteResponse> retornarTestes(
            @RequestHeader(name = "Authorization") String token, // Bearer ahsdjkahkjdahjksd
            @RequestBody InserirTesteRequest inserirTesteRequest
    ){
        return ResponseEntity.ok(testeService.salvaTeste(inserirTesteRequest,token.substring(7)));
    }
}
