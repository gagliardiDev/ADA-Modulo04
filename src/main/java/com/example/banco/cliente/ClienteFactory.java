package com.example.banco.cliente;

import java.time.LocalDate;

public interface ClienteFactory {
    Cliente criarCliente(String nome, String cpf, LocalDate dataNascimento, TIPO_PESSOA tipo, String status);
}
