package com.pdv.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vendas")
@Entity
public class Vendas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "data_venda", nullable = false)
	private LocalDateTime date;

	@ManyToOne
	@JoinColumn(name = "id_clientes")
	private Clientes clientes;

	@OneToMany(mappedBy = "vendas", fetch = FetchType.LAZY)
	private List<VendasProduto> vendas;
	
	@Column
	private BigDecimal valor;

}
