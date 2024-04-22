package br.fullstack.education.projetolabpcp.infra.exception;


public class NoResourceFoundException extends RuntimeException {
    public NoResourceFoundException(String message) {
        super(message);
    }
}