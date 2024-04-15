package br.fullstack.education.projetolabpcp.infra.exception;

public class CursoByIdNotFoundException extends RuntimeException {
    private final Long cursoId;

    public CursoByIdNotFoundException(Long id) {
        super("Curso não encontrado com id " + id);
        this.cursoId = id;
    }

    public Long getCursoId() {
        return cursoId;
    }
}