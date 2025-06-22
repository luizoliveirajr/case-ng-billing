package com.ngbilling.service;

import com.ngbilling.dto.ContaSimpleDTO;
import com.ngbilling.enums.EnumError;
import com.ngbilling.exception.ServiceException;
import com.ngbilling.model.Conta;
import com.ngbilling.repository.ContaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepository;

    @Test
    void deveCadastrarContaComSucesso() {
        // Arrange
        Long numeroConta = 1L;
        BigDecimal saldo = new BigDecimal("100.00");
        ContaSimpleDTO dto = new ContaSimpleDTO(numeroConta, saldo);

        when(contaRepository.existsById(numeroConta)).thenReturn(false);
        when(contaRepository.save(any(Conta.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        ContaSimpleDTO resultado = contaService.cadastrarConta(dto);

        // Assert
        assertNotNull(resultado);
        assertEquals(dto.getNumero_conta(), resultado.getNumero_conta());
        assertEquals(dto.getSaldo(), resultado.getSaldo());
        verify(contaRepository).save(any(Conta.class));
    }

    @Test
    void deveLancarExcecaoAoCadastrarContaJaExistente() {
        // Arrange
        Long numeroConta = 1L;
        ContaSimpleDTO dto = new ContaSimpleDTO(numeroConta, BigDecimal.TEN);
        when(contaRepository.existsById(numeroConta)).thenReturn(true);

        // Act & Assert
        ServiceException ex = assertThrows(ServiceException.class, () -> contaService.cadastrarConta(dto));
        assertEquals(EnumError.FALHA_AO_CADASTRAR_CONTA.getDescricao(), ex.getMensagem());
        assertEquals(EnumError.FALHA_AO_CADASTRAR_CONTA.getStatus().value(), ex.getHttpStatus());
    }

    @Test
    void deveAtualizarSaldoContaComSucesso() {
        // Arrange
        Conta conta = Conta.builder()
                .id(1L)
                .saldo(new BigDecimal("100.00"))
                .build();
        BigDecimal valor = new BigDecimal("30.00");

        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        // Act
        Conta resultado = contaService.atualizarSaldoContaAposTransacao(conta, valor);

        // Assert
        assertNotNull(resultado);
        assertEquals(new BigDecimal("70.00"), resultado.getSaldo());
        verify(contaRepository).save(conta);
    }

    @Test
    void deveLancarExcecaoAoAtualizarSaldoConta() {
        // Arrange
        Conta conta = Conta.builder()
                .id(1L)
                .saldo(new BigDecimal("100.00"))
                .build();
        BigDecimal valor = new BigDecimal("30.00");

        when(contaRepository.save(any(Conta.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        // Act & Assert
        ServiceException ex = assertThrows(ServiceException.class,
                () -> contaService.atualizarSaldoContaAposTransacao(conta, valor));
        assertEquals(EnumError.ERRO_GENERICO.getDescricao(), ex.getMensagem());
        assertEquals(EnumError.ERRO_GENERICO.getStatus().value(), ex.getHttpStatus());
    }

    @Test
    void deveBuscarContaComSucesso() {
        // Arrange
        Long numeroConta = 1L;
        Conta conta = Conta.builder()
                .id(numeroConta)
                .saldo(new BigDecimal("50.00"))
                .build();

        when(contaRepository.findById(numeroConta)).thenReturn(Optional.of(conta));

        // Act
        ContaSimpleDTO resultado = contaService.buscarConta(numeroConta);

        // Assert
        assertNotNull(resultado);
        assertEquals(numeroConta, resultado.getNumero_conta());
        assertEquals(new BigDecimal("50.00"), resultado.getSaldo());
    }

    @Test
    void deveLancarExcecaoQuandoContaNaoEncontrada() {
        // Arrange
        Long numeroConta = 99L;
        when(contaRepository.findById(numeroConta)).thenReturn(Optional.empty());

        // Act & Assert
        ServiceException ex = assertThrows(ServiceException.class, () -> contaService.buscarConta(numeroConta));
        assertEquals(EnumError.CONTA_NAO_ENCONTRADA.getDescricao(), ex.getMensagem());
        assertEquals(EnumError.CONTA_NAO_ENCONTRADA.getStatus().value(), ex.getHttpStatus());
    }

    @Test
    void deveBuscarContaPorIdComSucesso() {
        // Arrange
        Long numeroConta = 1L;
        Conta conta = Conta.builder().id(numeroConta).saldo(BigDecimal.TEN).build();

        when(contaRepository.findById(numeroConta)).thenReturn(Optional.of(conta));

        // Act
        Optional<Conta> resultado = contaService.buscarContaPorId(numeroConta);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(numeroConta, resultado.get().getId());
    }

}