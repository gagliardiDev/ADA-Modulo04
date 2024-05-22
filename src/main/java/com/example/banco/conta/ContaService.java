package com.example.banco.conta;

import com.example.banco.cliente.ClienteRepository;
import com.example.banco.cliente.TIPO_PESSOA;
import com.example.banco.usuario.Usuario;
import com.example.banco.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final UsuarioService usuarioService;
    private final ContaFactory contaFactory;

    @Autowired
    private UserDetailsService userDetailsService;

    private ContaDTO convertDto(Conta conta) {
        return this.modelMapper.map(conta, ContaDTO.class);
    }

    private Conta convertFromDto(ContaDTO contaDto) {
        return this.modelMapper.map(contaDto, Conta.class);
    }

    public List<ContaDTO> listarContas() {
        return this.contaRepository.findAll().stream()
                .map(this::convertDto)
                .collect(Collectors.toList());
    }

    public ContaDTO salvar(ContaDTO contaDto) {
        var cliente = clienteRepository.findById(contaDto.getClienteId()).orElseThrow();
        var conta = contaFactory.criarConta(contaDto.getSaldo(), TIPO_CONTA.valueOf(String.valueOf(contaDto.getTipo())), cliente);
        var savedConta = this.contaRepository.save(conta);
        return this.convertDto(savedConta);
    }

    public Optional<ContaDTO> buscarPorId(Long id) {
        return this.contaRepository.findById(id).map(this::convertDto);
    }

    public void excluir(Long id) {
        var conta = this.contaRepository.findById(id).orElseThrow();
        this.contaRepository.delete(conta);
    }

    public ContaDTO atualizar(ContaDTO contaDto) {
        var conta = this.contaRepository.findById(contaDto.getId()).orElseThrow();
        conta.setSaldo(contaDto.getSaldo());
        return this.convertDto(contaRepository.save(conta));
    }

    public ContaDTO sacar(ContaDTO contaDto) {
        var conta = this.contaRepository.findById(contaDto.getId()).orElseThrow();
        var cliente = conta.getCliente();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Usuario usuario = usuarioService.getByUsernameEntity(username);

            if (usuario.getCpf() == null || !usuario.getCpf().equals(cliente.getCpf())) {
                throw new RuntimeException("CPF do usuário autenticado não corresponde ao CPF do cliente associado à conta");
            }

            if (conta.getSaldo().compareTo(contaDto.getValor()) >= 0 && contaDto.getValor() > 0) {
                double taxa = 0;
                if (cliente.getTipo() == TIPO_PESSOA.PJ) {
                    taxa = contaDto.getValor() * 0.005;
                }
                conta.setSaldo(conta.getSaldo() - contaDto.getValor() - taxa);
                return this.convertDto(contaRepository.save(conta));
            } else {
                throw new RuntimeException("Saldo insuficiente para efetuar o saque");
            }
        } else {
            throw new RuntimeException("Usuário não autenticado");
        }
    }

    public ContaDTO transferir(ContaDTO contaDto) {
        var conta = this.contaRepository.findById(contaDto.getId()).orElseThrow();
        var contaDestino = this.contaRepository.findById(contaDto.getIdContaDestino()).orElseThrow();
        var cliente = conta.getCliente();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Usuario usuario = usuarioService.getByUsernameEntity(username);

            if (usuario.getCpf() == null || !usuario.getCpf().equals(cliente.getCpf())) {
                throw new RuntimeException("CPF do usuário autenticado não corresponde ao CPF do cliente associado à conta");
            }

            if (conta.getSaldo().compareTo(contaDto.getValor()) >= 0 && contaDto.getValor() > 0) {
                double taxa = 0;
                if (cliente.getTipo() == TIPO_PESSOA.PJ) {
                    taxa = contaDto.getValor() * 0.005;
                }
                conta.setSaldo(conta.getSaldo() - contaDto.getValor() - taxa);
                contaDestino.setSaldo(contaDestino.getSaldo() + contaDto.getValor());
                this.convertDto(contaRepository.save(conta));
                return this.convertDto(contaRepository.save(contaDestino));
            } else {
                throw new RuntimeException("Saldo insuficiente para efetuar a transferência");
            }
        } else {
            throw new RuntimeException("Usuário não autenticado");
        }
    }


    public ContaDTO depositar(ContaDTO contaDto) {
        var conta = this.contaRepository.findById(contaDto.getId()).orElseThrow();
        var cliente = conta.getCliente();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();


            Usuario usuario = usuarioService.getByUsernameEntity(username);

            if (usuario.getCpf() == null || !usuario.getCpf().equals(cliente.getCpf())) {
                throw new RuntimeException("CPF do usuário autenticado não corresponde ao CPF do cliente associado à conta");
            }

            if (contaDto.getValor() > 0) {
                conta.setSaldo(conta.getSaldo() + contaDto.getValor());
                return this.convertDto(contaRepository.save(conta));
            } else {
                throw new RuntimeException("Valor depositado menor ou igual a 0");
            }
        } else {
            throw new RuntimeException("Usuário não autenticado");
        }
    }



    public ContaDTO investir(ContaDTO contaDto) {
        var conta = this.contaRepository.findById(contaDto.getId()).orElseThrow();
        var cliente = conta.getCliente();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Usuario usuario = usuarioService.getByUsernameEntity(username);

            if (usuario.getCpf() == null || !usuario.getCpf().equals(cliente.getCpf())) {
                throw new RuntimeException("CPF do usuário autenticado não corresponde ao CPF do cliente associado à conta");
            }

            if (contaDto.getValor() > 0) {
                double rendimento = 0;
                if (cliente.getTipo() == TIPO_PESSOA.PF) {
                    rendimento = contaDto.getValor() * 0.01;
                } else if (cliente.getTipo() == TIPO_PESSOA.PJ) {
                    rendimento = contaDto.getValor() * 0.02;
                }
                conta.setSaldo(conta.getSaldo() + contaDto.getValor() + rendimento);
                return this.convertDto(contaRepository.save(conta));
            } else {
                throw new RuntimeException("Valor investido menor ou igual a 0");
            }
        } else {
            throw new RuntimeException("Usuário não autenticado");
        }
    }

}
