package br.com.magalu.config.exception;

import br.com.magalu.config.exception.response.CustomErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<CustomErrorResponse> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().stream().forEach(error -> {
            CustomErrorResponse customErrorResponse = CustomErrorResponse.builder()
                    .errorCode(error.getField()).errorMessage(error.getDefaultMessage()).errorType("MethodArgumentNotValidException").build();
            errors.add(customErrorResponse);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "Dados no formato errado.";
        try{
            message =  ex.getCause().getCause().getMessage();
        }catch (Exception e){}
        CustomErrorResponse customErrorResponse = CustomErrorResponse.builder()
                .errorCode("Dados invalidos").errorMessage(message).errorType("HttpMessageNotReadableException").build();
        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomErrorResponse> exception(Exception ex, WebRequest request) {
        CustomErrorResponse customErrorResponse = CustomErrorResponse.builder()
                .errorCode(ex.getMessage()).errorMessage(ex.getMessage()).errorType(ex.getClass().getName()).build();
        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }
}






















