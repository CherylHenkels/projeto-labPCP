package br.fullstack.education.projetolabpcp.infra.exception;

import br.fullstack.education.projetolabpcp.controller.dto.request.ErroRequest;
import br.fullstack.education.projetolabpcp.infra.exception.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.relation.InvalidRoleInfoException;

@ControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handler(Exception e) {
        ErroRequest erro = ErroRequest.builder()
                .codigo("500")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(500).body(erro);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handler(DataIntegrityViolationException e) {
        ErroRequest erro = ErroRequest.builder()
                .codigo("400")
                .mensagem("Requisição inválida, por exemplo, dados ausentes ou incorretos")
                .build();
        return ResponseEntity.status(400).body(erro);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handler(NotFoundException e) {
        ErroRequest erro = ErroRequest.builder()
                .codigo("404")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(404).body(erro);
    }

//    @ExceptionHandler(InvalidRoleException.class)
//    public ResponseEntity<?> handler(InvalidRoleException e) {
//        ErroRequest erro = ErroRequest.builder()
//                .codigo("400")
//                .mensagem(e.getMessage())
//                .build();
//        return ResponseEntity.status(400).body(erro);
//    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> handler(InvalidRequestException e) {
        ErroRequest erro = ErroRequest.builder()
                .codigo("400")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(400).body(erro);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handler(InvalidCredentialsException e) {
        ErroRequest erro = ErroRequest.builder()
                .codigo("401")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(401).body(erro);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handler(UserAlreadyExistsException e) {
        ErroRequest erro = ErroRequest.builder()
                .codigo("409")
                .mensagem(e.getMessage())
                .build();
        return ResponseEntity.status(409).body(erro);
    }

//    @ExceptionHandler(AlunoByIdNotFoundException.class)
//    public ResponseEntity<?> handler(AlunoByIdNotFoundException e) {
//        String mensagem = "Aluno não encontrado com id: " + e.getAlunoId();
//        ErroRequest erro = ErroRequest.builder()
//                .codigo("404")
//                .mensagem(mensagem)
//                .build();
//        return ResponseEntity.status(404).body(erro);
//    }
//
//    @ExceptionHandler(DocenteByIdNotFoundException.class)
//    public ResponseEntity<?> handler(DocenteByIdNotFoundException e) {
//        String mensagem = "Docente não encontrado com id: " + e.getDocenteId();
//        ErroRequest erro = ErroRequest.builder()
//                .codigo("404")
//                .mensagem(mensagem)
//                .build();
//        return ResponseEntity.status(404).body(erro);
 //   }



}