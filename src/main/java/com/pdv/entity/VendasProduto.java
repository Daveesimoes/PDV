package com.pdv.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vendas_produtos")
@Entity
public class VendasProduto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "vendas_id", nullable = false)
	private Vendas vendas;
	
	@ManyToOne
	@JoinColumn(name = "produtos_id", nullable = false)
	private Produtos produtos;
	
	@Column(nullable = false)
	private int quantidade;
	
	@Column
	private BigDecimal valorTotal;
		
	
}
