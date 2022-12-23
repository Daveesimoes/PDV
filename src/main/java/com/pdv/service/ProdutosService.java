package com.pdv.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdv.dto.ProdutosDTO;
import com.pdv.entity.Produtos;
import com.pdv.exception.NaoExisteException;
import com.pdv.repository.ProdutosRepository;

@Service
public class ProdutosService {

	@Autowired
	private ProdutosRepository produtosRepository;

	public List<ProdutosDTO> listarAll() {
		return produtosRepository.findAll().stream()
				.map(produtos -> new ProdutosDTO(produtos.getId(), produtos.getQuantidade()))
				.collect(Collectors.toList());
	}

	public ProdutosDTO findById(long id) {
		Produtos produtos = produtosRepository.findById(id)
				.orElseThrow(() -> new NaoExisteException("Produto não encontrado!"));

		return new ProdutosDTO(produtos.getId(),  produtos.getQuantidade());
	}

	public ProdutosDTO adicionar(Produtos produtos) {
		Produtos produtosSalvo = produtosRepository.save(produtos);

		return new ProdutosDTO(produtosSalvo.getId(), produtosSalvo.getQuantidade());
	}

	public ProdutosDTO alterar(Produtos produtos) {
		produtosRepository.save(produtos);

		return new ProdutosDTO(produtos.getId(), produtos.getQuantidade());
	}

	public void delete(long id) {
		if (!produtosRepository.existsById(id)) {
			throw new NaoExisteException("Produto não encontrado!");
		} else {
			produtosRepository.deleteById(id);
		}
	}

}
