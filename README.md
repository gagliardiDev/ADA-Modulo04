# Princípios SOLID

## . Princípio da Responsabilidade Única (SRP)
- **Classe:** `ClienteController`
- **Caminho:** `src/main/java/com/example/banco/cliente/ClienteController.java`
- **Linhas:** 1-50
- **Explicação:** A classe `ClienteController` é responsável apenas por gerenciar as requisições relacionadas a clientes. Todas as funcionalidades relacionadas a clientes são centralizadas aqui, sem misturar com lógica de negócio ou persistência de dados.

- **Classe:** `ContaController`
- **Caminho:** `src/main/java/com/example/banco/conta/ContaController.java`
- **Linhas:** 
- **Explicação:** A classe `ContaController` é responsável apenas por gerenciar as requisições relacionadas a contas. Todas as funcionalidades relacionadas a contas são centralizadas aqui.

## . Princípio do Aberto/Fechado (OCP)
- **Interface:** `ClienteFactory`
- **Caminho:** `src/main/java/com/example/banco/cliente/ClienteFactory.java`
- **Linhas:** 
- **Explicação:** A interface `ClienteFactory` define um contrato para criação de objetos `Cliente`. Essa interface pode ser estendida para criar novos tipos de clientes sem modificar o código existente, facilitando a expansão do sistema.

## . Princípio da Substituição de Liskov (LSP)
- **Classe:** `ClienteFactoryImpl`
- **Caminho:** `src/main/java/com/example/banco/cliente/ClienteFactoryImpl.java`
- **Linhas:** 
- **Explicação:** A classe `ClienteFactoryImpl` implementa a interface `ClienteFactory`. Qualquer instância de `ClienteFactoryImpl` pode substituir uma instância de `ClienteFactory` sem quebrar a funcionalidade do sistema, respeitando o princípio de substituição de Liskov.

## . Princípio da Segregação de Interfaces (ISP)
- **Interface:** `MaiorDeIdade, ClienteExistente'
- **Caminho:** `src/main/java/com/example/banco/util/ClienteExistente.java`  e MaiorDeIdade.Java
- **Linhas:** 
- **Explicação:** A interface `ClienteExistente` e `MaiorDeIdade` é específica para operações de persistência relacionadas a `Cliente`. Ao segregar interfaces específicas para diferentes funcionalidades, evitamos interfaces gordas e promovemos a implementação de contratos específicos.

# Padrões de Projeto Utilizados no Projeto

## . Builder Pattern
- **Classe:** `Cliente`
- **Caminho:** `com.example.banco.cliente.Cliente`
- **Linha do código:** `@Builder`
- **Descrição:** O padrão Builder é utilizado para facilitar a criação de objetos complexos. No caso da classe `Cliente`, a notação `@Builder` do LOMBOK permite a construção de instâncias da classe `Cliente` com uma interface mais legível e fluida.

## . Factory Pattern
- **Classe:** `ClienteFactory`
- **Caminho:** `com.example.banco.cliente.ClienteFactoryImpl`
- **Descrição:** O padrão Factory é utilizado para criar objetos sem expor a lógica de criação ao cliente e referenciar a nova classe gerada por meio de uma interface comum. Aqui, a fábrica `ClienteFactoryImpl` encapsula a lógica de criação de clientes.

- **Classe:** `ContaFactory`
- **Caminho:** `com.example.banco.cliente.ContaFactoryImpl`
- **Descrição:** O padrão Factory é utilizado para criar objetos sem expor a lógica de criação ao cliente e referenciar a nova classe gerada por meio de uma interface comum. Aqui, a fábrica `ClienteFactoryImpl` encapsula a lógica de criação de clientes.

## . Singleton Pattern
- **Classe:** `ModelMapper`
- **Caminho:** `com.example.banco.BancoApplication`
- **Descrição:** O padrão Singleton é utilizado para garantir que uma classe tenha apenas uma instância e forneça um ponto de acesso global a essa instância. O bean `ModelMapper` é configurado como singleton no contexto do Spring, garantindo que a mesma instância seja usada em toda a aplicação.


## . Decorator Pattern
- **Classe:** `JwtAuthFilter`
- **Caminho:** `com.example.banco.security.WebSecurityConfig`
- **Linha do código:** `.addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)`
- **Descrição:** O padrão Decorator é utilizado para adicionar funcionalidades a objetos dinamicamente. No `WebSecurityConfig`, o filtro `JwtAuthFilter` é adicionado antes do `UsernamePasswordAuthenticationFilter`, decorando o processo de autenticação padrão com a lógica de autenticação JWT.
