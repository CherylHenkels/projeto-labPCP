package br.fullstack.education.projetolabpcp.controller.dto.request;

public record LoginRequest (
        String usuario,
        String senha
//        @JsonFormat(pattern = "dd/MM/yyyy") //formatar a partir do Json
//        LocalDate localDate,
//
//        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") //formatar a partir do Json
//        LocalDateTime localDateTime
){
}
