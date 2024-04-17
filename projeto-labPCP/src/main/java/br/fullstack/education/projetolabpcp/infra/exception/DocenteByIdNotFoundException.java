package br.fullstack.education.projetolabpcp.infra.exception;

public class DocenteByIdNotFoundException extends RuntimeException {
    private final Long docenteId;

    public DocenteByIdNotFoundException(Long id) {
        super("Docente não encontrado com id " + id);
        this.docenteId = id;
    }

    public Long getDocenteId() {
        return docenteId;
    }
}