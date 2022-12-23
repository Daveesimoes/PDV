package com.pdv.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pdv.dto.InfoProdutosDTO;
import com.pdv.dto.InfoVendasDTO;
import com.pdv.entity.Clientes;
import com.pdv.entity.Produtos;
import com.pdv.entity.Vendas;
import com.pdv.entity.VendasProduto;
import com.pdv.exception.NaoExisteException;
import com.pdv.exception.OperacaoInvalidaException;
import com.pdv.repository.ClientesRepository;
import com.pdv.repository.ProdutosRepository;
import com.pdv.repository.VendasProdutoRepository;
import com.pdv.repository.VendasRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendasService {

	private final ClientesRepository clientesRepository;
	private final ProdutosRepository produtosRepository;
	private final VendasRepository vendasRepository;
	private final VendasProdutoRepository vendasProdutoRepository;

	public List<InfoVendasDTO> findAll() {
		return vendasRepository.findAll().stream().map(vendas -> getInfoVendas(vendas)).collect(Collectors.toList());
	}

	private InfoVendasDTO getInfoVendas(Vendas vendas) {
		InfoVendasDTO infoVendasDTO = new InfoVendasDTO();
		infoVendasDTO.setCliente(vendas.getClientes().getNome());
		infoVendasDTO.setData(vendas.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
		infoVendasDTO.setProdutos(getInfoProdutos(vendas.getVendas()));
		infoVendasDTO.setValor(vendas.getValor());
		
	
		return infoVendasDTO;
	}
	
	private List<InfoProdutosDTO> getInfoProdutos(List<VendasProduto> vendas) {
		return vendas.stream().map(venda -> {
			InfoProdutosDTO infoProdutosDTO = new InfoProdutosDTO();
			infoProdutosDTO.setId(venda.getId());
			infoProdutosDTO.setDescricao(venda.getProdutos().getDescricao());
			infoProdutosDTO.setQuantidade(venda.getQuantidade());
			
			return infoProdutosDTO;
		}).collect(Collectors.toList());

	}

	public void salvar(Vendas venda) {

		Clientes cliente = clientesRepository.findById(venda.getClientes().getId())
				.orElseThrow(() -> new NaoExisteException("Cliente não encontrado!"));

		venda.setClientes(cliente);
		venda.setDate(LocalDateTime.now());
		List<VendasProduto> produtos = getVendaProduto(venda.getVendas());

		
		venda = vendasRepository.save(venda);

		salvarVendaProduto(produtos, venda);

	}

	private List<VendasProduto> getVendaProduto(List<VendasProduto> produtos) {

		if (produtos.isEmpty()) {
			throw new OperacaoInvalidaException("Não pode adicionar uma venda sem produtos!");
		}

		return produtos.stream().map(produto -> {
			Produtos item = produtosRepository.getReferenceById(produto.getId());

			VendasProduto vendasProduto = new VendasProduto();
			vendasProduto.setProdutos(item);
			vendasProduto.setQuantidade(produto.getQuantidade());
			
			if (item.getQuantidade() == 0) {
				throw new NaoExisteException("Não tem esse produto no estoque!");
			} else if (item.getQuantidade() < produto.getQuantidade()) {
				throw new OperacaoInvalidaException(String.format(
						"A quantidade de itens à venda (%s) é maior que a quantidade disponível em estoque (%s)!",
						produto.getQuantidade(), item.getQuantidade()));
			}
			
					
			int total = item.getQuantidade() - produto.getQuantidade();
			item.setQuantidade(total);
			produtosRepository.save(item);

			return vendasProduto;
		}).collect(Collectors.toList());
	}

	private void salvarVendaProduto(List<VendasProduto> produtos, Vendas vendas) {
		for (VendasProduto produto : produtos) {
			produto.setVendas(vendas);
			vendasProdutoRepository.save(produto);
		}
	}

	public InfoVendasDTO vendasById(Long id) {
		Vendas vendas = vendasRepository.findById(id)
				.orElseThrow(() -> new NaoExisteException("Venda não encontrada!"));
		return getInfoVendas(vendas);
	}

}