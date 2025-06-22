package com.ngbilling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ContaSimpleDTO {

    private Long numero_conta;
    private BigDecimal saldo;

}
