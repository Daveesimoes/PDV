package com.pdv.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutosDTO {
	
	@Column(name = "produtos_id")
	private long produtosId;
	private String descricao;
	private int quantidade;

}
