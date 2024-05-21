package com.example.banco.conta;

import com.example.banco.cliente.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ContaFactoryImpl implements ContaFactory {

    @Override
    public Conta criarConta(Double saldo, TIPO_CONTA tipo, Cliente cliente) {
        return Conta.builder()
                .saldo(saldo)
                .tipo(tipo)
                .cliente(cliente)
                .build();
    }
}
