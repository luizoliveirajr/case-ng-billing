package com.ngbilling.service.strategy;

import java.math.BigDecimal;

public interface TransacaoStrategy {
    BigDecimal calcularValorComTaxa(BigDecimal valor);
}
