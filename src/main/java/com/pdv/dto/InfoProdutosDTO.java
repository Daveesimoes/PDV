package com.pdv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoProdutosDTO {
	
	private long id;
	private String descricao;
	private int quantidade;
	

}
