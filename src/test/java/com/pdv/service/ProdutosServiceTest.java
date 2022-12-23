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
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pdv.dto.ProdutosDTO;
import com.pdv.entity.Produtos;
import com.pdv.exception.NaoExisteException;
import com.pdv.repository.ProdutosRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProdutosServiceTest {

	@InjectMocks
	private ProdutosService produtosService;

	@Mock
	private ProdutosRepository produtosRepository;

	@Test
	public void buscarTodosTest() {
		List<Produtos> list = produtosRepository.findAll();

		assertNotNull(list);
	}

	@Test
	public void buscarPeloIdTest() {

		Optional<Produtos> user = Optional.of(new Produtos(1L, "Coca-Cola", new BigDecimal("9"), 20));

		when(produtosRepository.findById(anyLong())).thenReturn(user);

		Produtos produtos = new Produtos();
		produtos = user.get();

		ProdutosDTO retorno = produtosService.findById(produtos.getId());

		assertEquals(ProdutosDTO.class, retorno.getClass());

	}

	@Test
	public void buscarPeloIdInexistenteTest() {
		when(produtosRepository.findById(any())).thenReturn(Optional.empty());

		assertThrows(NaoExisteException.class, () -> produtosService.findById(2L));

	}

	@Test
	public void adicionarTest() {
		Produtos x = new Produtos(1L, "Coca-Cola", new BigDecimal("9"), 20);

		when(produtosRepository.save(any())).thenReturn(x);
		ProdutosDTO retorno = produtosService.adicionar(x);

		assertNotNull(retorno);
	}

	@Test
	public void atualizarTest() {
		Produtos x = new Produtos(1L, "Coca-Cola", new BigDecimal("9"), 20);

		when(produtosRepository.save(any())).thenReturn(x);
		ProdutosDTO retorno = produtosService.adicionar(x);

		assertNotNull(retorno);
	}
	
	@Test
	public void deleteProdutoTest() {
		Optional<Produtos> user = Optional.of(new Produtos(1L, "Coca-Cola", new BigDecimal("9"), 20));

		when(produtosRepository.existsById(anyLong())).thenReturn(true);
		
		produtosService.delete(user.get().getId());
		
		verify(produtosRepository, times(1)).deleteById(user.get().getId());
		
	}
	
	@Test
	public void deleteProdutoNaoExisenteTest() {
				
		assertThrows(NaoExisteException.class,() -> produtosService.delete(1));
		
	}
	
}
