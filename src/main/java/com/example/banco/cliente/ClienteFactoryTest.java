package com.example.banco.cliente;

import org.testng.annotations.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ClienteFactoryTest {

    private final ClienteFactory clienteFactory = new ClienteFactoryImpl();

    @Test
    void criarClienteTest() {
        Cliente cliente = clienteFactory.criarCliente(
                "Teste Nome",
                "12345678901",
                LocalDate.of(1990, 1, 1),
                TIPO_PESSOA.PF,
                null
        );

        assertNotNull(cliente);
        assertEquals("Teste Nome", cliente.getNome());
        assertEquals("12345678901", cliente.getCpf());
        assertEquals(LocalDate.of(1990, 1, 1), cliente.getData_nascimento());
        assertEquals(TIPO_PESSOA.PF, cliente.getTipo());
        assertEquals("Ativo", cliente.getStatus());
    }
}
