package com.pdv.exception;

public class NaoExisteException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NaoExisteException(String mensagem) {
		super(mensagem);
	}

}
