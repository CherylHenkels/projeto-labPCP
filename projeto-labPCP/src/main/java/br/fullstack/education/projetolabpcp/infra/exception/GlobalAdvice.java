package br.fullstack.education.projetolabpcp.infra.exception;

import br.fullstack.education.projetolabpcp.controller.dto.request.ErroRequest;
import br.fullstack.education.projetolabpcp.infra.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    @ExceptionHandler(AlunoByIdNotFoundException.class)
    public ResponseEntity<?> handler(AlunoByIdNotFoundException e) {
        String mensagem = "Aluno não encontrado com id: " + e.getAlunoId();
        ErroRequest erro = ErroRequest.builder()
                .codigo("404")
                .mensagem(mensagem)
                .build();
        return ResponseEntity.status(404).body(erro);
    }

    @ExceptionHandler(DocenteByIdNotFoundException.class)
    public ResponseEntity<?> handler(DocenteByIdNotFoundException e) {
        String mensagem = "Docente não encontrado com id: " + e.getDocenteId();
        ErroRequest erro = ErroRequest.builder()
                .codigo("404")
                .mensagem(mensagem)
                .build();
        return ResponseEntity.status(404).body(erro);
    }

//    @ExceptionHandler(DisciplinaByIdNotFoundException.class)
//    public ResponseEntity<?> handler(DisciplinaByIdNotFoundException e) {
//        String mensagem = "Disciplina não encontrada com id: " + e.getDisciplinaId();
//        ErroRequest erro = ErroRequest.builder()
//                .codigo("404")
//                .mensagem(mensagem)
//                .build();
//        return ResponseEntity.status(404).body(erro);
//    }
//
//    @ExceptionHandler(DisciplinaMatriculaByIdNotFoundException.class)
//    public ResponseEntity<?> handler(DisciplinaMatriculaByIdNotFoundException e) {
//        String mensagem = "Matrícula em disciplina não encontrada com id: " + e.getDisciplinaMatriculaId();
//        ErroRequest erro = ErroRequest.builder()
//                .codigo("404")
//                .mensagem(mensagem)
//                .build();
//        return ResponseEntity.status(404).body(erro);
//    }
//
//    @ExceptionHandler(OperacaoNaoPermitidaException.class)
//    public ResponseEntity<?> handleOperacaoNaoPermitida(OperacaoNaoPermitidaException e) {
//        String mensagem = "Existem notas lançadas para esta matrícula, não é possível excluir.";
//        ErroRequest erro = ErroRequest.builder()
//                .codigo("422") // Usando o código 422 Unprocessable Entity
//                .mensagem(mensagem)
//                .build();
//        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
//    }
//
//    @ExceptionHandler(MatriculaDuplicadaException.class)
//    public ResponseEntity<?> handleMatriculaDuplicada(MatriculaDuplicadaException e) {
//        ErroRequest erro = ErroRequest.builder()
//                .codigo("409") // HTTP 409 Conflict
//                .mensagem(e.getMessage())
//                .build();
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
//    }

}