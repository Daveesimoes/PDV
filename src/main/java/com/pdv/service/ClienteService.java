package com.pdv.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdv.dto.ClientesDTO;
import com.pdv.entity.Clientes;
import com.pdv.exception.NaoExisteException;
import com.pdv.repository.ClientesRepository;

@Service
public class ClienteService {

	@Autowired
	private ClientesRepository clientesRepository;

	public List<ClientesDTO> findAll() {
		return clientesRepository.findAll().stream()
				.map(cliente -> new ClientesDTO(cliente.getId(), cliente.getNome(), cliente.isAtivo()))
				.collect(Collectors.toList());
	}

	public ClientesDTO findById(long id) {
		Optional<Clientes> optional = clientesRepository.findById(id);

		if (!optional.isPresent()) {
			throw new NaoExisteException("Cliente não encontrado!");
		}

		Clientes cliente = optional.get();
		return new ClientesDTO(cliente.getId(), cliente.getNome(), cliente.isAtivo());
	}

	public ClientesDTO adicionar(Clientes clientes) {
		clientesRepository.save(clientes);
		return new ClientesDTO(clientes.getId(), clientes.getNome(), clientes.isAtivo());
	}

	public ClientesDTO atualizar(Clientes clientes) {

		Optional<Clientes> optional = clientesRepository.findById(clientes.getId());

		if (!optional.isPresent()) {
			throw new NaoExisteException("Cliente não encontrado!");
		} else {
			clientesRepository.save(clientes);
			return new ClientesDTO(clientes.getId(), clientes.getNome(), clientes.isAtivo());
		}
	}

	public void deletar(long id) {
		clientesRepository.deleteById(id);
	}
}
