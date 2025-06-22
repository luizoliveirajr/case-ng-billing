package com.ngbilling.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EnumError {

    FALHA_AO_CADASTRAR_CONTA("Esta conta já existe.", HttpStatus.CONFLICT, "Error"),
    ERRO_GENERICO("Falha no sistema, contacte o suporte.", HttpStatus.BAD_REQUEST, "Error"),
    CONTA_NAO_ENCONTRADA("Conta não encontrada", HttpStatus.NOT_FOUND, "Error"),
    SALDO_INSUFICIENTE("Saldo Insuficiente", HttpStatus.NOT_FOUND, "Error"),
    FORMA_PAGAMENTO_DESCONHECIDA("Forma de pagamento desconhecida", HttpStatus.BAD_REQUEST, "Error");

    private final String descricao;
    private final HttpStatus status;
    private final String tipo;

}
