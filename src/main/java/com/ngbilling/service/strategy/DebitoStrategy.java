package com.ngbilling.service.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DebitoStrategy implements TransacaoStrategy{
    private static final BigDecimal TAXA = new BigDecimal("0.03");

    @Override
    public BigDecimal calcularValorComTaxa(BigDecimal valor) {
        return valor.add(valor.multiply(TAXA));
    }
}
