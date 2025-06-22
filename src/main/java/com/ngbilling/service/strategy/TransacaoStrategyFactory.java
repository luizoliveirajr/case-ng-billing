package com.ngbilling.service.strategy;

import com.ngbilling.enums.EnumFormaPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class TransacaoStrategyFactory {

    private final Map<EnumFormaPagamento, TransacaoStrategy> estrategias = new EnumMap<>(EnumFormaPagamento.class);

    @Autowired
    public TransacaoStrategyFactory(PixStrategy pix, DebitoStrategy debito, CreditoStrategy credito) {
        estrategias.put(EnumFormaPagamento.PIX, pix);
        estrategias.put(EnumFormaPagamento.CARTAO_DEBITO, debito);
        estrategias.put(EnumFormaPagamento.CARTAO_CREDITO, credito);
    }

    public TransacaoStrategy getStrategy(EnumFormaPagamento formaPagamento) {
        return estrategias.get(formaPagamento);
    }

}
