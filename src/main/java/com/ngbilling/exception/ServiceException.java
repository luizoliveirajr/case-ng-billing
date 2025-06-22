package com.ngbilling.exception;

import com.ngbilling.enums.EnumError;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(value = {"stackTrace", "cause", "message", "suppressed", "localizedMessage"})
public class ServiceException extends RuntimeException {
    private final String mensagem;
    private final Integer httpStatus;
    private final String tipo;

    public ServiceException(EnumError error) {
        this.mensagem = error.getDescricao();
        this.httpStatus = error.getStatus().value();
        this.tipo = error.getTipo();
    }
}
