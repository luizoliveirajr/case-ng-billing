package com.ngbilling.enums;

import com.ngbilling.exception.ServiceException;

public enum EnumFormaPagamento {

    PIX("P"),
    CARTAO_CREDITO("C"),
    CARTAO_DEBITO("D");

    private final String sigla;

    EnumFormaPagamento(String sigla) {
        this.sigla = sigla;
    }

    public String getSigla() {
        return sigla;
    }

    public static EnumFormaPagamento fromSigla(String sigla) {
        for (EnumFormaPagamento forma : values()) {
            if (forma.getSigla().equalsIgnoreCase(sigla)) {
                return forma;
            }
        }
        throw new ServiceException(EnumError.FORMA_PAGAMENTO_DESCONHECIDA);
    }
}
