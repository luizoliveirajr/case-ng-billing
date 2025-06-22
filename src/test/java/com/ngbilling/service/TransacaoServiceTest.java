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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private ContaService contaService;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private TransacaoStrategyFactory transacaoStrategyFactory;

    @Mock
    private TransacaoStrategy strategy;

    @Test
    void deveRealizarTransacaoComSucesso() {
        // Arrange
        Long numeroConta = 1L;
        BigDecimal saldoInicial = new BigDecimal("100.00");
        BigDecimal valorTransacao = new BigDecimal("10.00");
        BigDecimal valorComTaxa = new BigDecimal("10.30");
        EnumFormaPagamento formaDebito = EnumFormaPagamento.CARTAO_DEBITO;

        TransacaoRequestDTO request = new TransacaoRequestDTO(formaDebito.getSigla(), numeroConta, valorTransacao);

        Conta conta = Conta.builder()
                .id(numeroConta)
                .saldo(saldoInicial)
                .build();

        Transacao transacaoSalva = Transacao.builder()
                .id(1L)
                .conta(conta)
                .formaPagamento(formaDebito)
                .build();

        when(contaService.buscarContaPorId(numeroConta)).thenReturn(Optional.of(conta));
        when(transacaoStrategyFactory.getStrategy(formaDebito)).thenReturn(strategy);
        when(strategy.calcularValorComTaxa(valorTransacao)).thenReturn(valorComTaxa);
        conta.setSaldo(saldoInicial.subtract(valorComTaxa));
        when(contaService.atualizarSaldoContaAposTransacao(any(Conta.class), eq(valorComTaxa))).thenReturn(conta);
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacaoSalva);

        // Act
        TransacaoResponseDTO response = transacaoService.realizarTransacao(request);

        // Assert
        assertEquals(numeroConta, response.numero_conta());
        assertEquals(saldoInicial.subtract(valorComTaxa), response.saldo());
        verify(contaService).atualizarSaldoContaAposTransacao(conta, valorComTaxa);
        verify(transacaoRepository).save(any(Transacao.class));
    }

    @Test
    void deveLancarExcecaoQuandoSaldoInsuficiente() {
        Long numeroConta = 1L;
        BigDecimal saldo = new BigDecimal("5.00");
        BigDecimal valorTransacao = new BigDecimal("10.00");
        BigDecimal valorComTaxa = new BigDecimal("10.30");

        TransacaoRequestDTO request = new TransacaoRequestDTO(EnumFormaPagamento.PIX.getSigla(), numeroConta, valorTransacao);

        Conta conta = Conta.builder()
                .id(numeroConta)
                .saldo(saldo)
                .build();

        when(contaService.buscarContaPorId(numeroConta)).thenReturn(Optional.of(conta));
        when(transacaoStrategyFactory.getStrategy(EnumFormaPagamento.PIX)).thenReturn(strategy);
        when(strategy.calcularValorComTaxa(valorTransacao)).thenReturn(valorComTaxa);

        ServiceException ex = assertThrows(ServiceException.class, () -> {
            transacaoService.realizarTransacao(request);
        });

        assertEquals(EnumError.SALDO_INSUFICIENTE.getDescricao(), ex.getMensagem());
        assertEquals(EnumError.SALDO_INSUFICIENTE.getStatus().value(), ex.getHttpStatus());
        assertEquals(EnumError.SALDO_INSUFICIENTE.getTipo(), ex.getTipo());
    }
}