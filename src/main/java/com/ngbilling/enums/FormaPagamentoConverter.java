package com.ngbilling.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FormaPagamentoConverter implements AttributeConverter<EnumFormaPagamento, String> {

    @Override
    public String convertToDatabaseColumn(EnumFormaPagamento forma) {
        return forma != null ? forma.getSigla() : null;
    }

    @Override
    public EnumFormaPagamento convertToEntityAttribute(String codigo) {
        return codigo != null ? EnumFormaPagamento.fromSigla(codigo) : null;
    }
}
