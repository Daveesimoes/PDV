package com.pdv.controller;

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

import com.pdv.dto.ResponseDTO;
import com.pdv.entity.Clientes;
import com.pdv.exception.NaoExisteException;
import com.pdv.service.ClienteService;

import jakarta.validation.Valid;

@Validated
@Controller
@RequestMapping("/clientes")
public class ClientesController {

	private ClienteService clienteService;

	public ClientesController(@Autowired ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@GetMapping
	public ResponseEntity listar() {
		return new ResponseEntity<>(clienteService.findAll(), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity findById(@PathVariable long id) {
		return new ResponseEntity<>(clienteService.findById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity adicionar(@RequestBody @Valid Clientes clientes) {

		try {
			clientes.setAtivo(true);
			return new ResponseEntity<>(clienteService.adicionar(clientes), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity atualizar(@RequestBody @Valid Clientes clientes) {
		try {
			return new ResponseEntity<>(clienteService.atualizar(clientes), HttpStatus.OK);
		} catch (NaoExisteException e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage(), clientes), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable long id) {
		try {
			clienteService.deletar(id);
			return new ResponseEntity<>("Cliente deletado com sucesso!", HttpStatus.OK);
		} catch (NaoExisteException e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage(), id ), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
