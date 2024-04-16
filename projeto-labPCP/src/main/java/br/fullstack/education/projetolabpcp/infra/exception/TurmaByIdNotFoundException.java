package br.fullstack.education.projetolabpcp.infra.exception;

public class TurmaByIdNotFoundException extends RuntimeException {
    private final Long turmaId;

    public TurmaByIdNotFoundException(Long id) {
        super("Turma não encontrado com id " + id);
        this.turmaId = id;
    }

    public Long getTurmaId() {
        return turmaId;
    }
}