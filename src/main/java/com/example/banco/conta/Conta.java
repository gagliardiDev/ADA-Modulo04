package com.example.banco.conta;

import javax.persistence.*;

import com.example.banco.cliente.Cliente;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Setter
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double saldo;

    @Enumerated(EnumType.STRING)
    private TIPO_CONTA tipo;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;


}
