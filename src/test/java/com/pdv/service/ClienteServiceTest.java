package com.pdv.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pdv.dto.ClientesDTO;
import com.pdv.entity.Clientes;
import com.pdv.exception.NaoExisteException;
import com.pdv.repository.ClientesRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class ClienteServiceTest {

	@InjectMocks
	private ClienteService clienteService;

	@Mock
	private ClientesRepository clientesRepository;

	@Test
	public void buscarPeloIdTest() {
		Optional<Clientes> user = Optional.of(new Clientes(1L, "DAVE", true, null));

		when(clientesRepository.findById(anyLong())).thenReturn(user);

		Clientes cliente = new Clientes();
		cliente = user.get();

		ClientesDTO retorno = clienteService.findById(cliente.getId());

		assertEquals(ClientesDTO.class, retorno.getClass());
		assertEquals(cliente.getId(), retorno.getId());
		assertEquals(cliente.getNome(), retorno.getNome());
		assertEquals(cliente.getVendas(), null);

	}

	@Test
	public void buscarPeloIdInexistenteTest() {
		when(clientesRepository.findById(any())).thenReturn(Optional.empty());

		assertThrows(NaoExisteException.class, () -> clienteService.findById(2L));

	}

	@Test
	public void buscarTodosTest() {
		List<ClientesDTO> list = clienteService.findAll();

		assertNotNull(list);
	}

	@Test
	public void adicionarTest() {
		when(clientesRepository.save(any())).thenReturn(new Clientes());
		ClientesDTO retorno = clienteService.adicionar(new Clientes());

		assertNotNull(retorno);
	}

	@Test
	public void atualizarTest() {

		when(clientesRepository.save(any())).thenReturn(new Clientes());
		ClientesDTO retorno = clienteService.adicionar(new Clientes());

		assertNotNull(retorno);

	}

	@Test
	public void atualizarClienteInexistenteTest() {

		when(clientesRepository.save(any())).thenReturn(null);

		assertThrows(NaoExisteException.class, () -> clienteService.atualizar(new Clientes()));

	}
}
