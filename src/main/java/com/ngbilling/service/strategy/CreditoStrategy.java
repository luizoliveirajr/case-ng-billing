package com.ngbilling.service.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CreditoStrategy implements TransacaoStrategy {
    private static final BigDecimal TAXA = new BigDecimal("0.05");

    @Override
    public BigDecimal calcularValorComTaxa(BigDecimal valor) {
        return valor.add(valor.multiply(TAXA));
    }
}
