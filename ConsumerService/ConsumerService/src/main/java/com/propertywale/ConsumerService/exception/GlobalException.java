package com.propertywale.ConsumerService.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(value = NoSuchElementException.class)
	public ResponseEntity<String> handleWithNoSuchElement(NoSuchElementException ex){
		return new ResponseEntity<String>("No User Found for this id",HttpStatusCode.valueOf(404));
	}
}
