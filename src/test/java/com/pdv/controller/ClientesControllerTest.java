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
import com.pdv.dto.ClientesDTO;
import com.pdv.service.ClienteService;

@WebMvcTest(ClientesController.class)
class ClientesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ClienteService clienteService;

	@Test
	public void listarTest() throws Exception {

		mockMvc.perform(get("/clientes")).andExpect(status().isOk());
	}

	@Test
	public void adicionarTest() throws Exception {
		ClientesDTO user = new ClientesDTO();
		user.setNome("hushuahsua");
		user.setAtivo(true);

		mockMvc.perform(post("/clientes").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isCreated());
	}

	@Test
	public void adicionarSemNomeTest() throws Exception {
		ClientesDTO user = new ClientesDTO();

		mockMvc.perform(post("/clientes").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isBadRequest());
	}

	@Test
	public void atualizarTest() throws Exception {
		ClientesDTO user = new ClientesDTO();
		user.setId(1L);
		user.setNome("suahhsiua");
		user.setAtivo(true);

		mockMvc.perform(put("/clientes").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isOk());

	}

	@Test
	public void atualizarClienteInexistenteTest() throws Exception {
		ClientesDTO user = new ClientesDTO();

		mockMvc.perform(
				put("/clientes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void deletarCliente() throws Exception {
		ClientesDTO user = new ClientesDTO();
		user.setId(1L);
		user.setNome("Suhaushuai");
		user.setAtivo(true);

		mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}
