package com.example.kino.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
public class HomePageExeption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HomePageExeption() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HomePageExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public HomePageExeption(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public HomePageExeption(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public HomePageExeption(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}