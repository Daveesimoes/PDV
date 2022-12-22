package com.pdv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pdv.dto.ClientesDTO;
import com.pdv.entity.Clientes;
import com.pdv.service.ClienteService;

import jakarta.validation.Valid;

@Validated
@Controller
@RequestMapping("/clientes")
public class ClientesController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public ResponseEntity<List<ClientesDTO>> listar() {
		return new ResponseEntity<>(clienteService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientesDTO> findById(@PathVariable long id) {
		return new ResponseEntity<>(clienteService.findById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ClientesDTO> adicionar(@RequestBody @Valid Clientes clientes) {

		return new ResponseEntity<>(clienteService.adicionar(clientes), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<ClientesDTO> atualizar(@RequestBody @Valid Clientes clientes) {
		return new ResponseEntity<>(clienteService.atualizar(clientes), HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ClientesDTO> deletar(@PathVariable long id) {
		clienteService.deletar(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
