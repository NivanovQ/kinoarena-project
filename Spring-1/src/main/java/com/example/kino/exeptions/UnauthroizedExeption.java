package com.example.kino.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthroizedExeption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnauthroizedExeption() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UnauthroizedExeption(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public UnauthroizedExeption(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UnauthroizedExeption(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UnauthroizedExeption(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
