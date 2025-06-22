package com.ngbilling.service.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
class CreditoStrategyTest {

    private final CreditoStrategy strategy = new CreditoStrategy();

    @Test
    void deveAplicarTaxaDeCreditoCorretamente() {
        BigDecimal valor = new BigDecimal("100.00");
        BigDecimal esperado = new BigDecimal("105.0000");

        BigDecimal resultado = strategy.calcularValorComTaxa(valor);

        assertEquals(esperado, resultado);
    }
}