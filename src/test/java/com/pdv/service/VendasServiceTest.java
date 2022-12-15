package com.pdv.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.validator.internal.util.privilegedactions.NewInstance;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pdv.dto.ClientesDTO;
import com.pdv.dto.InfoVendasDTO;
import com.pdv.dto.ProdutosDTO;
import com.pdv.dto.VendasDTO;
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
		List<ProdutosDTO> produto = new ArrayList<>();
		produto.add(new ProdutosDTO(1L, "Coca-cola", 30));

		VendasDTO venda = new VendasDTO();
		venda.setClienteId(vendas.getId());
		venda.setProdutos(produto);

		when(produtosRepository.getReferenceById(anyLong()))
				.thenReturn(new Produtos(1L, "Coca-Cola", new BigDecimal("9"), 40));

		Long retorno = vendasService.salvar(venda);

		assertNotNull(retorno);

	}

	@Test
	void testSalvarQuantidadeMaior() {
		Optional<Clientes> user = Optional.of(vendaPadrao().getClientes());
		when(clientesRepository.findById(anyLong())).thenReturn(user);
		Vendas vendas = vendaPadrao();
		when(vendasRepository.save(any())).thenReturn(vendas);
		List<ProdutosDTO> produto = new ArrayList<>();
		produto.add(new ProdutosDTO(1L, "Coca-cola", 30));

		VendasDTO venda = new VendasDTO();
		venda.setClienteId(vendas.getId());
		venda.setProdutos(produto);

		when(produtosRepository.getReferenceById(anyLong()))
				.thenReturn(new Produtos(1L, "Coca-Cola", new BigDecimal("9"), 20));

		assertThrows(OperacaoInvalidaException.class, () -> vendasService.salvar(venda));

	}

	@Test
	void testSalvarNaoTemEstoque() {
		Optional<Clientes> user = Optional.of(vendaPadrao().getClientes());
		when(clientesRepository.findById(anyLong())).thenReturn(user);
		Vendas vendas = vendaPadrao();
		when(vendasRepository.save(any())).thenReturn(vendas);
		List<ProdutosDTO> produto = new ArrayList<>();
		produto.add(new ProdutosDTO(1L, "Coca-cola", 30));

		VendasDTO venda = new VendasDTO();
		venda.setClienteId(vendas.getId());
		venda.setProdutos(produto);

		when(produtosRepository.getReferenceById(anyLong()))
				.thenReturn(new Produtos(1L, "Coca-Cola", new BigDecimal("9"), 0));

		assertThrows(NaoExisteException.class, () -> vendasService.salvar(venda));

	}

	@Test
	void testSalvarListaProdutosVazia() {
		Optional<Clientes> user = Optional.of(vendaPadrao().getClientes());
		when(clientesRepository.findById(anyLong())).thenReturn(user);
		Vendas vendas = vendaPadrao();
		when(vendasRepository.save(any())).thenReturn(vendas);
		List<ProdutosDTO> produto = new ArrayList<>();
		
		VendasDTO venda = new VendasDTO();
		venda.setClienteId(vendas.getId());
		venda.setProdutos(produto);

		assertThrows(OperacaoInvalidaException.class, () -> vendasService.salvar(venda));

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
		Produtos produtos = new Produtos(1L, "Coca-Cola", new BigDecimal("9"), 20);
		List<VendasProduto> list = new ArrayList<>();
		list.add(new VendasProduto(1L, new Vendas(1L, LocalDate.now(), new Clientes(1L, "DAVE", true, null), null),
				produtos, 20));
		Clientes clientes = new Clientes(1L, "DAVE", true, null);
		Vendas vendas = new Vendas(1L, LocalDate.now(), clientes, list);

		return vendas;

	}

}
