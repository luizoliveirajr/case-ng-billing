package com.ngbilling.service.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PixStrategyTest {

    private final PixStrategy strategy = new PixStrategy();

    @Test
    void deveAplicarTaxaDePixCorretamente() {
        BigDecimal valor = new BigDecimal("100.0000");

        BigDecimal resultado = strategy.calcularValorComTaxa(valor);

        assertEquals(valor, resultado);
    }
}