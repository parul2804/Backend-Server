package com.example.demo;

import com.example.demo.takehome.exceptions.ErrorResponse;
import com.example.demo.takehome.exceptions.NoSuchWidgetException;
import com.example.demo.takehome.exceptions.WidgetAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    if (ex.getMessage().contains("WidgetAlreadyExistsException")) {
      // Log that we are skipping this validation as it's handled by a custom handler
    //  log.error("Skipping validation error handling for WidgetAlreadyExistsException, as it will be handled by custom exception handler.");
      // Return null to allow the custom handler to deal with this exception
      return null;
    }
    List<String> errorList = ex
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .map((field) -> field.getField() + ": " + field.getDefaultMessage())
        .toList();

    return ResponseEntity.badRequest().body(errorList);
  }

  @ExceptionHandler(NoSuchWidgetException.class)
  public ResponseEntity<Object> handleNoSuchWidgetException(NoSuchWidgetException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            ex.getErrorCode(),
            ex.getMessage()
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(WidgetAlreadyExistsException.class)
  public ResponseEntity<Object> handleWidgetAlreadyExistsException(WidgetAlreadyExistsException ex) {
    System.out.println("handleWidgetAlreadyExistsException");
    ErrorResponse errorResponse = new ErrorResponse(
            ex.getErrorCode(),
            ex.getMessage()
    );
    // Return the response with a Conflict status (HTTP 409)
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

}
