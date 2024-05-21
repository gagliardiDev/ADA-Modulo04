package com.example.banco.cliente;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final ClienteFactory clienteFactory;

    private ClienteDTO convertDto(Cliente cliente) {
        return this.modelMapper.map(cliente, ClienteDTO.class);
    }

    private Cliente convertFromDto(ClienteDTO clienteDto) {
        return this.modelMapper.map(clienteDto, Cliente.class);
    }

    public List<ClienteDTO> listarClientes() {
        return this.clienteRepository.findAll().stream()
                .map(this::convertDto)
                .collect(Collectors.toList());
    }

    public ClienteDTO salvar(ClienteDTO clienteDto) {
        var cliente = clienteFactory.criarCliente(
                clienteDto.getNome(),
                clienteDto.getCpf(),
                clienteDto.getData_nascimento(),
                TIPO_PESSOA.valueOf(String.valueOf(clienteDto.getTipo())),
                clienteDto.getStatus()
        );
        var savedCliente = this.clienteRepository.save(cliente);
        return this.convertDto(savedCliente);
    }

    public Optional<ClienteDTO> findById(Long id) {
        return this.clienteRepository.findById(id).map(this::convertDto);
    }

    public void excluir(Long id) {
        var cliente = this.clienteRepository.findById(id).orElseThrow();
        this.clienteRepository.delete(cliente);
    }

    public ClienteDTO atualizar(Long id, ClienteDTO clienteDto) {
        var cliente = this.clienteRepository.findById(id).orElseThrow();
        cliente.setNome(clienteDto.getNome());
        cliente.setData_nascimento(clienteDto.getData_nascimento());
        cliente.setCpf(clienteDto.getCpf());
        cliente.setStatus(clienteDto.getStatus());
        cliente.setTipo(TIPO_PESSOA.valueOf(String.valueOf(clienteDto.getTipo())));
        return this.convertDto(clienteRepository.save(cliente));
    }
}
