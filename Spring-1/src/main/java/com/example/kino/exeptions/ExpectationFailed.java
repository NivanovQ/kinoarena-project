package com.example.kino.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class ExpectationFailed extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExpectationFailed() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExpectationFailed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ExpectationFailed(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ExpectationFailed(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ExpectationFailed(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}


}
