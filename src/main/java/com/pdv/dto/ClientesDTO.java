package com.pdv.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientesDTO {
	
	@NotBlank
	private Long id;
	@NotBlank
	private String nome;
	private boolean ativo;
}
