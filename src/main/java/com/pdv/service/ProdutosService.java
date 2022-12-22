package com.pdv.service;

import java.util.List;
import java.util.Optional;
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
				.map(produtos -> new ProdutosDTO(produtos.getId(), produtos.getDescricao(), produtos.getQuantidade()))
				.collect(Collectors.toList());

	}

	public ProdutosDTO findById(long id) {
		Optional<Produtos> optional = produtosRepository.findById(id);

		if (!optional.isPresent()) {
			throw new NaoExisteException("Produto não encontrado!");
		}

		Produtos produtos = optional.get();
		return new ProdutosDTO(produtos.getId(), produtos.getDescricao(), produtos.getQuantidade());

	}

	public ProdutosDTO adicionar(Produtos produtos) {
		produtosRepository.save(produtos);
		return new ProdutosDTO(produtos.getId(), produtos.getDescricao(), produtos.getQuantidade());
	}

	public ProdutosDTO alterar(Produtos produtos) {

		Optional<Produtos> optional = produtosRepository.findById(produtos.getId());

		if (!optional.isPresent()) {
			throw new NaoExisteException("Produto não encontrado!");
		} else {
			produtosRepository.save(produtos);
			return new ProdutosDTO(produtos.getId(), produtos.getDescricao(), produtos.getQuantidade());
		}

	}

	public void delete(long id) {
		Optional<Produtos> optional = produtosRepository.findById(id);

		if (!optional.isPresent()) {
			throw new NaoExisteException("Produto não encontrado!");
		} else {
			produtosRepository.deleteById(optional.get().getId());
		}
	}

}
