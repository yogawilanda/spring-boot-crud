package com.example.demo.config;

import org.springframework.http.HttpStatus;

// Custom Exception : Self explanatory, 
// supaya membungkus Exception menjadi custom sesuai kebutuhan user/sistem
public class ExceptionCustom extends RuntimeException {
	private final HttpStatus status;

	// Constructor untuk pesan error
    public ExceptionCustom(String message, HttpStatus status) {
        super(message);

	  this.status = status;
    }

	// Custom throw untuk return 400, 500,
	public HttpStatus getStatus() {
        return status;
    }
}
