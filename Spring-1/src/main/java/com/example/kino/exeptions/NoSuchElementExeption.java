package com.example.kino.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchElementExeption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchElementExeption() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoSuchElementExeption(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NoSuchElementExeption(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoSuchElementExeption(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NoSuchElementExeption(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
