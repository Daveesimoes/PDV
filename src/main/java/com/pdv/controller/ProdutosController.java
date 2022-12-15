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
import com.pdv.entity.Produtos;
import com.pdv.exception.NaoExisteException;
import com.pdv.service.ProdutosService;

import jakarta.validation.Valid;

@Validated
@Controller
@RequestMapping("/produtos")
public class ProdutosController {

	private ProdutosService produtosService;

	public ProdutosController(@Autowired ProdutosService produtosService) {
		this.produtosService = produtosService;
	}

	@GetMapping
	public ResponseEntity listar() {
		return new ResponseEntity<>(produtosService.listarAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity findById(@PathVariable long id) {
		return new ResponseEntity<>(produtosService.findById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity adicionarProduto(@RequestBody @Valid Produtos produto) {
		try {
			return new ResponseEntity<>(produtosService.adicionar(produto), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity alterarProduto(@RequestBody @Valid Produtos produto) {
		try {
			return new ResponseEntity<>(produtosService.alterar(produto), HttpStatus.OK);
		} catch (NaoExisteException e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage(), produto), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deletarProduto(@PathVariable long id) {
		try {
			produtosService.delete(id);
			return new ResponseEntity<>("Cliente deletado com sucesso!", HttpStatus.OK);
		} catch (NaoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
