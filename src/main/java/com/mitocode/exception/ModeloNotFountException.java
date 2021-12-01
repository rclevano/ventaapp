package com.mitocode.exception;


//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModeloNotFountException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ModeloNotFountException(String mensaje) {
		super(mensaje);
	}
}
