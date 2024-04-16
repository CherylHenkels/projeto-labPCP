package br.fullstack.education.projetolabpcp.infra.exception;

public class MateriaByIdNotFoundException extends RuntimeException {
    private final Long materiaId;

    public MateriaByIdNotFoundException(Long id) {
        super("Materia não encontrado com id " + id);
        this.materiaId = id;
    }

    public Long getMateriaId() {
        return materiaId;
    }
}