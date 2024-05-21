package com.example.banco.cliente;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ClienteFactoryImpl implements ClienteFactory {

    @Override
    public Cliente criarCliente(String nome, String cpf, LocalDate dataNascimento, TIPO_PESSOA tipo, String status) {
        return Cliente.builder()
                .nome(nome)
                .cpf(cpf)
                .data_nascimento(dataNascimento)
                .tipo(tipo)
                .status(status != null ? status : "Ativo")
                .build();
    }
}
