package br.fullstack.education.projetolabpcp.infra.exception;

public class NotaByIdNotFoundException extends RuntimeException {
    private final Long notaId;

    public NotaByIdNotFoundException(Long id) {
        super("Nota não encontrado com id " + id);
        this.notaId = id;
    }

    public Long getNotaId() {
        return notaId;
    }
}