package com.pdv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.pdv.dto.InfoVendasDTO;
import com.pdv.entity.Vendas;
import com.pdv.service.VendasService;

import jakarta.validation.Valid;

@Validated
@Controller
@RequestMapping("/vendas")
public class VendasController {

	@Autowired
	private VendasService vendasService;

	@GetMapping
	public ResponseEntity<List<InfoVendasDTO>> listar() {
		return new ResponseEntity<>(vendasService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<InfoVendasDTO> procurarId(@PathVariable Long id) {
		return new ResponseEntity<>(vendasService.vendasById(id), HttpStatus.OK);
	}
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping
	public void vender(@RequestBody @Valid Vendas vendas) {
		vendasService.salvar(vendas);
	}
}
