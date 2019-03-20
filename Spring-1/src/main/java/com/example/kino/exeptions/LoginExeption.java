package com.example.kino.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginExeption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginExeption() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public LoginExeption(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public LoginExeption(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public LoginExeption(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
