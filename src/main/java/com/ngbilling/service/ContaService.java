package com.ngbilling.service;

import com.ngbilling.dto.ContaSimpleDTO;
import com.ngbilling.enums.EnumError;
import com.ngbilling.exception.ServiceException;
import com.ngbilling.model.Conta;
import com.ngbilling.repository.ContaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContaService {

    private final ContaRepository contaRepository;

    @Transactional
    public ContaSimpleDTO cadastrarConta(ContaSimpleDTO dto) {
        if (contaRepository.existsById(dto.getNumero_conta())) {
            throw new ServiceException(EnumError.FALHA_AO_CADASTRAR_CONTA);
        }

        if(dto.getSaldo().compareTo(BigDecimal.ZERO) < 0){
            throw new ServiceException(EnumError.SALDO_INSUFICIENTE);
        }

        try {
            Conta novaConta = gerarEntidadeApartirDoDTO(dto);
            contaRepository.save(novaConta);
            return dto;
        } catch (Exception e) {
            log.error("{} - FALHA AO CADASTRAR CONTA: {}", LocalDateTime.now(), e.getMessage(), e);
            throw new ServiceException(EnumError.ERRO_GENERICO);
        }
    }

    public Conta atualizarSaldoContaAposTransacao(Conta conta, BigDecimal valor){
        try{
            conta.setSaldo(conta.getSaldo().subtract(valor));
            return this.contaRepository.save(conta);
        }catch (Exception e){
            log.error("{} - FALHA AO ATUALIZAR SALDO CONTA APOS TRANSACAO: {}", LocalDateTime.now(), e.getMessage(), e);
            throw new ServiceException(EnumError.ERRO_GENERICO);
        }
    }

    public ContaSimpleDTO buscarConta(Long numeroConta) {
        Optional<Conta> contaOptional = this.buscarContaPorId(numeroConta);
        return contaOptional.map(this::converterEntidadeParaDTO).orElseThrow(() -> new ServiceException(EnumError.CONTA_NAO_ENCONTRADA));
    }

    private ContaSimpleDTO converterEntidadeParaDTO(Conta conta) {
        return ContaSimpleDTO.builder()
                .numero_conta(conta.getId())
                .saldo(conta.getSaldo())
                .build();
    }

    public Optional<Conta> buscarContaPorId(Long id){
        return this.contaRepository.findById(id);
    }

    private Conta gerarEntidadeApartirDoDTO(ContaSimpleDTO dto) {
        return Conta.builder()
                .id(dto.getNumero_conta())
                .saldo(dto.getSaldo())
                .build();
    }
}
