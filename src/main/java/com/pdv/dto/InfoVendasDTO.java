package com.pdv.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoVendasDTO {
	
	private String cliente;
	private String data;
	private List<InfoProdutosDTO> produtos;
	private BigDecimal valor;
}
