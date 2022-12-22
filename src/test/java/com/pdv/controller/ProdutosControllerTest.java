package com.pdv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdv.dto.ProdutosDTO;
import com.pdv.service.ProdutosService;

@WebMvcTest(ProdutosController.class)
class ProdutosControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ProdutosService produtosService;

	@Test
	public void listarTest() throws Exception {

		mockMvc.perform(get("/produtos")).andExpect(status().isOk());
	}

	@Test
	public void findById() throws Exception {
		ProdutosDTO user = new ProdutosDTO();
		user.setDescricao("suahhsiua");
		user.setProdutosId(2L);
		user.setQuantidade(80);

		mockMvc.perform(MockMvcRequestBuilders.get("/produtos/2").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	public void adicionarTest() throws Exception {
		ProdutosDTO user = new ProdutosDTO();
		user.setDescricao("suahhsiua");
		user.setProdutosId(2L);
		user.setQuantidade(80);

		mockMvc.perform(post("/produtos").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isCreated());
	}

	@Test
	public void adicionarSemNomeTest() throws Exception {
		ProdutosDTO user = new ProdutosDTO();

		mockMvc.perform(post("/produtos").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isBadRequest());
	}

	@Test
	public void atualizarTest() throws Exception {
		ProdutosDTO user = new ProdutosDTO();
		user.setProdutosId(1L);
		user.setDescricao("suahhsiua");
		user.setQuantidade(80);

		mockMvc.perform(put("/produtos").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isOk());

	}

	@Test
	public void atualizarProdutoInexistenteTest() throws Exception {
		ProdutosDTO user = new ProdutosDTO();

		mockMvc.perform(
				put("/produtos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deletarProdutoTest() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/produtos/2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
