package com.example.banco.conta;

import com.example.banco.cliente.Cliente;

public interface ContaFactory {
    Conta criarConta(Double saldo, TIPO_CONTA tipo, Cliente cliente);
}
