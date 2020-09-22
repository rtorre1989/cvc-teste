package br.com.cvc.teste.cvcteste.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public final ResponseEntity<Object> handleConstraintViolationExceptions(
      ConstraintViolationException ex) {
    String exceptionResponse = String.format("Invalid input parameters: %s\n", ex.getMessage());
    return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
  }
}