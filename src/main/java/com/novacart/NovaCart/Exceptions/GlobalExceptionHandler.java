package com.novacart.NovaCart.Exceptions;

import com.novacart.NovaCart.DTO.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleALLException(Exception ex, WebRequest request) {
        ResponseDTO errorResponse = ResponseDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDTO> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ResponseDTO errorResponse = ResponseDTO.builder().status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<ResponseDTO> handleInvalidCredentialException(InvalidCredentialException ex, WebRequest request) {
        ResponseDTO errorResponse = ResponseDTO.builder().status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
