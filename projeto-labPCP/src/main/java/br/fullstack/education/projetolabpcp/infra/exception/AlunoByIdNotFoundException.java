package br.fullstack.education.projetolabpcp.infra.exception;

public class AlunoByIdNotFoundException extends RuntimeException {
    private final Long alunoId;

    public AlunoByIdNotFoundException(Long id) {
        super("Aluno não encontrado com id " + id);
        this.alunoId = id;
    }

    public Long getAlunoId() {
        return alunoId;
    }
}