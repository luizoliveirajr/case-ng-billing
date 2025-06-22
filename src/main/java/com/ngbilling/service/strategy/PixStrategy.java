package com.ngbilling.service.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PixStrategy implements TransacaoStrategy{

    @Override
    public BigDecimal calcularValorComTaxa(BigDecimal valor) {
        return valor;
    }
}
