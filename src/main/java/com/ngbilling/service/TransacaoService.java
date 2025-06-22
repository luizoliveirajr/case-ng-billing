package com.ngbilling.service;

import com.ngbilling.dto.TransacaoRequestDTO;
import com.ngbilling.dto.TransacaoResponseDTO;
import com.ngbilling.enums.EnumError;
import com.ngbilling.enums.EnumFormaPagamento;
import com.ngbilling.exception.ServiceException;
import com.ngbilling.model.Conta;
import com.ngbilling.model.Transacao;
import com.ngbilling.repository.TransacaoRepository;
import com.ngbilling.service.strategy.TransacaoStrategy;
import com.ngbilling.service.strategy.TransacaoStrategyFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {
    private final ContaService contaService;
    private final TransacaoRepository transacaoRepository;
    private final TransacaoStrategyFactory transacaoStrategyFactory;

    @Transactional
    public TransacaoResponseDTO realizarTransacao(TransacaoRequestDTO dto) {
        Conta conta = this.contaService.buscarContaPorId(dto.getNumero_conta())
                .orElseThrow(() -> new ServiceException(EnumError.CONTA_NAO_ENCONTRADA));

        EnumFormaPagamento enumFormaPagamento = EnumFormaPagamento.fromSigla(dto.getForma_pagamento());

        TransacaoStrategy strategy = this.transacaoStrategyFactory.getStrategy(enumFormaPagamento);

        BigDecimal valorComTaxa = strategy.calcularValorComTaxa(dto.getValor());

        if (conta.getSaldo().compareTo(valorComTaxa) < 0) {
            throw new ServiceException(EnumError.SALDO_INSUFICIENTE);
        }

        conta = this.contaService.atualizarSaldoContaAposTransacao(conta, valorComTaxa);
        Transacao transacao = this.salvarTransacao(conta, enumFormaPagamento);

        return converterTransacaoParaDTO(transacao);
    }

    private TransacaoResponseDTO converterTransacaoParaDTO(Transacao transacao) {
        return new TransacaoResponseDTO(transacao.getConta().getId(), transacao.getConta().getSaldo());
    }

    private Transacao salvarTransacao(Conta conta, EnumFormaPagamento formaPagamento){
        try{
            Transacao transacao = Transacao.builder()
                    .conta(conta)
                    .formaPagamento(formaPagamento)
                    .build();
            return this.transacaoRepository.save(transacao);
        }catch (Exception e){
            log.error("{} - FALHA AO SALVAR TRANSACAO: {}", LocalDateTime.now(), e.getMessage(), e);
            throw new ServiceException(EnumError.ERRO_GENERICO);
        }
    }
}
