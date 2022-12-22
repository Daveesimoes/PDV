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

import com.pdv.dto.ProdutosDTO;
import com.pdv.entity.Produtos;
import com.pdv.service.ProdutosService;

import jakarta.validation.Valid;

@Validated
@Controller
@RequestMapping("/produtos")
public class ProdutosController {

	@Autowired
	private ProdutosService produtosService;

	@GetMapping
	public ResponseEntity<List<ProdutosDTO>> listar() {
		return new ResponseEntity<>(produtosService.listarAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProdutosDTO> findById(@PathVariable long id) {
		return new ResponseEntity<>(produtosService.findById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ProdutosDTO> adicionarProduto(@RequestBody @Valid Produtos produto) {
		return new ResponseEntity<>(produtosService.adicionar(produto), HttpStatus.CREATED);

	}

	@PutMapping
	public ResponseEntity<ProdutosDTO> alterarProduto(@RequestBody @Valid Produtos produto) {
		return new ResponseEntity<>(produtosService.alterar(produto), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletarProduto(@PathVariable long id) {
		produtosService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
