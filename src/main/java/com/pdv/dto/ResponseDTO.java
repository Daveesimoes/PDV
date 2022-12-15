package com.pdv.dto;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ResponseDTO<T> {
	
	@Getter
	private List<String> mensagem;
	
	@Getter
	private T data;


	public ResponseDTO(List<String> mensagem, T data) {
		this.mensagem = mensagem;
		this.data = data;
	}
	
	public ResponseDTO(String mensagem, T data) {
		this.mensagem = Arrays.asList(mensagem);
		this.data = data;
	}

}
