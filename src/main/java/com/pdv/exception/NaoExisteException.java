package com.pdv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NaoExisteException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NaoExisteException(String mensagem) {
		super(mensagem);
	}

}
