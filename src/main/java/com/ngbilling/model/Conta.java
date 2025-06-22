package com.ngbilling.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "conta")
public class Conta {

    @Id
    private Long id;
    @Column(name = "saldo")
    private BigDecimal saldo;

}
