package com.pdv.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pdv.dto.InfoVendasDTO;
import com.pdv.dto.ProdutosDTO;
import com.pdv.entity.Clientes;
import com.pdv.entity.Produtos;
import com.pdv.entity.Vendas;
import com.pdv.entity.VendasProduto;
import com.pdv.exception.NaoExisteException;
import com.pdv.exception.OperacaoInvalidaException;
import com.pdv.repository.ClientesRepository;
import com.pdv.repository.ProdutosRepository;
import com.pdv.repository.VendasProdutoRepository;
import com.pdv.repository.VendasRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class VendasServiceTest {

	@InjectMocks
	private VendasService vendasService;

	@Mock
	private VendasRepository vendasRepository;

	@Mock
	private ClientesRepository clientesRepository;

	@Mock
	private ProdutosRepository produtosRepository;

	@Mock
	private VendasProdutoRepository vendasProdutoRepository;

	@Test
	void testFindAll() {
		List<InfoVendasDTO> list = vendasService.findAll();

		assertNotNull(list);
	}

	@Test
	void testSalvar() {
		Optional<Clientes> user = Optional.of(vendaPadrao().getClientes());
		when(clientesRepository.findById(anyLong())).thenReturn(user);
		Vendas vendas = vendaPadrao();
		when(vendasRepository.save(any())).thenReturn(vendas);
		List<VendasProduto> produto = new ArrayList<>();
		produto.add(new VendasProduto(1L,new Vendas(), new Produtos(), 30, new BigDecimal(30)));

		
		when(produtosRepository.getReferenceById(anyLong()))
				.thenReturn(new Produtos(1L, "Coca-Cola", new BigDecimal("9"), 40));

		vendasService.salvar(vendas);

		verify(vendasRepository, times(1)).save(vendas);
	}

	@Test
	void testSalvarQuantidadeMaior() {
		Optional<Clientes> user = Optional.of(vendaPadrao().getClientes());
		when(clientesRepository.findById(anyLong())).thenReturn(user);
		Vendas vendas = vendaPadrao();
		when(vendasRepository.save(any())).thenReturn(vendas);
						
		when(produtosRepository.getReferenceById(anyLong()))
				.thenReturn(new Produtos(1L, "Coca-Cola", new BigDecimal("9"), 5));

		assertThrows(OperacaoInvalidaException.class, () -> vendasService.salvar(vendas));

	}

	@Test
	void testSalvarNaoTemEstoque() {
		Optional<Clientes> user = Optional.of(vendaPadrao().getClientes());
		when(clientesRepository.findById(anyLong())).thenReturn(user);
		Vendas vendas = vendaPadrao();
		when(vendasRepository.save(any())).thenReturn(vendas);
		List<ProdutosDTO> produto = new ArrayList<>();
		produto.add(new ProdutosDTO(1L, 30));

		when(produtosRepository.getReferenceById(anyLong()))
				.thenReturn(new Produtos(1L, "Coca-Cola", new BigDecimal("9"), 0));

		assertThrows(NaoExisteException.class, () -> vendasService.salvar(vendas));

	}

	@Test
	void testSalvarListaProdutosVazia() {
		Optional<Clientes> user = Optional.of(vendaPadrao().getClientes());
		when(clientesRepository.findById(anyLong())).thenReturn(user);
		Vendas vendas = vendaPadrao();
		when(vendasRepository.save(any())).thenReturn(vendas);
		vendas.setVendas(new ArrayList<VendasProduto>());		

		assertThrows(OperacaoInvalidaException.class, () -> vendasService.salvar(vendas));

	}

	@Test
	void testVendasById() {
		Optional<Vendas> user = Optional.of(vendaPadrao());

		when(vendasRepository.findById(anyLong())).thenReturn(user);

		Vendas vendas = new Vendas();
		vendas = user.get();

		InfoVendasDTO retorno = vendasService.vendasById(vendas.getId());

		assertEquals(InfoVendasDTO.class, retorno.getClass());

	}

	@Test
	void testVendasByIdInexistente() {
		when(vendasRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(NaoExisteException.class, () -> vendasService.vendasById(2L));

	}

	private Vendas vendaPadrao() {
		Clientes clientes = new Clientes(1L, "DAVE", true, null);
		Produtos produtos = new Produtos(1L, "Coca-Cola", new BigDecimal("9"), 20);
		List<VendasProduto> list = new ArrayList<>();
		list.add(new VendasProduto(1L, new Vendas(1L, LocalDateTime.now(), clientes, null, new BigDecimal(30)),
				produtos, 20, new BigDecimal(30)));
				Vendas vendas = new Vendas(1L, LocalDateTime.now(), clientes, list, new BigDecimal(80));

		return vendas;

	}

}
