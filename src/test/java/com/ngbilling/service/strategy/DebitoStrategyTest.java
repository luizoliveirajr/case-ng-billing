package com.ngbilling.service.strategy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DebitoStrategyTest {

    private final DebitoStrategy strategy = new DebitoStrategy();

    @Test
    void deveAplicarTaxaDeDebitoCorretamente() {
        BigDecimal valor = new BigDecimal("100.00");
        BigDecimal esperado = new BigDecimal("103.0000");

        BigDecimal resultado = strategy.calcularValorComTaxa(valor);

        assertEquals(esperado, resultado);
    }
}