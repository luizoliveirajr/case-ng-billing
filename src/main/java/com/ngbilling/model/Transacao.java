package com.ngbilling.model;

import com.ngbilling.enums.EnumFormaPagamento;
import com.ngbilling.enums.FormaPagamentoConverter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    @Convert(converter = FormaPagamentoConverter.class)
    @Column(name = "formaPagamento")
    private EnumFormaPagamento formaPagamento;

}
