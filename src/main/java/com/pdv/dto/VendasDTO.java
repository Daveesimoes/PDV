package com.pdv.dto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendasDTO {
	
	@NotNull
	@Column(name = "clientes_id")
	private long clienteId;
	
	List<ProdutosDTO> produtos;

	
}
