package com.pdv.controller;

import java.time.LocalDate;
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
import com.pdv.dto.ResponseDTO;
import com.pdv.dto.VendasDTO;
import com.pdv.entity.Clientes;
import com.pdv.entity.Vendas;
import com.pdv.entity.VendasProduto;
import com.pdv.exception.NaoExisteException;
import com.pdv.exception.OperacaoInvalidaException;
import com.pdv.service.VendasService;

import jakarta.validation.Valid;

@Validated
@Controller
@RequestMapping("/vendas")
public class VendasController {

	private VendasService vendasService;
	
	public VendasController(@Autowired VendasService vendasService) {
		this.vendasService = vendasService;
	}
	
	@GetMapping
	public ResponseEntity listar() {
		return new ResponseEntity<>(new ResponseDTO<List<InfoVendasDTO>>("", vendasService.findAll()), HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity procurarId(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(new ResponseDTO<>("", vendasService.vendasById(id)), HttpStatus.OK);
		} catch (NaoExisteException | OperacaoInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping
	public ResponseEntity vender(@RequestBody @Valid VendasDTO vendasDTO) {
		try {
			long id = vendasService.salvar(vendasDTO);
			return new ResponseEntity<>(new ResponseDTO<>("",vendasService.vendasById(id)), HttpStatus.CREATED);

		} catch (NaoExisteException | OperacaoInvalidaException e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDTO<>(e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
