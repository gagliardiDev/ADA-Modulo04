package com.example.banco.cliente;

import com.example.banco.util.Pessoa;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cliente extends Pessoa {

    @Column(unique = true)
    private String cpf;
    private LocalDate data_nascimento;
    private String status;
    @Enumerated(EnumType.STRING)
    private TIPO_PESSOA tipo;

    @Builder
    public Cliente(Long id, String nome, String cpf, LocalDate data_nascimento, String status, TIPO_PESSOA tipo) {
        super(id, nome);
        this.cpf = cpf;
        this.data_nascimento = data_nascimento;
        this.status = status;
        this.tipo = tipo;
    }
}
