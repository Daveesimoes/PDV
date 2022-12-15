package com.pdv.exception;

public class NaoExisteException extends RuntimeException{
	
	public NaoExisteException(String mensagem) {
		super(mensagem);
	}

}
