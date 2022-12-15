package com.pdv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdv.dto.ProdutosDTO;
import com.pdv.dto.VendasDTO;
import com.pdv.service.VendasService;

@WebMvcTest(VendasController.class)
class VendasControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private VendasService vendasService;

	@Test
	public void listarTest() throws Exception {

		mockMvc.perform(get("/vendas")).andExpect(status().isOk());
	}

	@Test
	public void venderTest() throws Exception {
		List<ProdutosDTO> list = new ArrayList<>();
				
		VendasDTO user = new VendasDTO();
		user.setClienteId(1L);
		user.setProdutos(list);

		mockMvc.perform(post("/vendas").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isCreated());
	}

	@Test
	public void venderSemIdTest() throws Exception {
				
		
		mockMvc.perform(post("/vendas").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void procurarByIdTest() throws Exception {
		List<ProdutosDTO> list = new ArrayList<>();
				
		VendasDTO user = new VendasDTO();
		user.setClienteId(1L);
		user.setProdutos(list);

		mockMvc.perform(get("/vendas/1").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isOk());
	}	
} 
