package com.ngbilling.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class TransacaoRequestDTO {

    private String forma_pagamento;
    private Long numero_conta;
    private BigDecimal valor;
}
