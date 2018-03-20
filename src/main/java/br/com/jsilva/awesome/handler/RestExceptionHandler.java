package br.com.jsilva.awesome.handler;

import br.com.jsilva.awesome.error.ErrorDetails;
import br.com.jsilva.awesome.error.ResourceNotFoundDetails;
import br.com.jsilva.awesome.error.ResourceNotFoundException;
import br.com.jsilva.awesome.error.ValidationErrorDetails;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException){
        val rnfDetails =  ResourceNotFoundDetails.Builder
                .newBuilder()
                .corporation(".::NewTec::.")
                .timeStamp(new Date().getTime())
                .status(HttpStatus.NOT_FOUND.value())
                .title("Resource not found")
                .detail(rfnException.getMessage())
                .developerMessage(rfnException.getClass().getName())
                .build();
        return new ResponseEntity<>(rnfDetails,HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manvException,
                                                                            HttpHeaders headers,
                                                                            HttpStatus status,
                                                                            WebRequest request) {

        val fieldErrors = manvException.getBindingResult().getFieldErrors();
        val fields = fieldErrors.stream().map(x -> x.getField()).collect(Collectors.joining(","));
        val fieldNessages = fieldErrors.stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(","));

        val rnfDetails = ValidationErrorDetails.Builder
                .newBuilder()
                .corporation(".::NewTec::.")
                .timeStamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Field validation error")
                .detail("Field validation error") // Estava vindo muito grande o manvException.getMessage()
                .developerMessage(manvException.getClass().getName())
                .field(fields)
                .fieldMessage(fieldNessages)
                .build();
        return new ResponseEntity<>(rnfDetails,HttpStatus.BAD_REQUEST);
    }

    //O restante cai aqui.
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        val errorDetails =  ErrorDetails.Builder
                .newBuilder()
                .corporation(".::NewTec::.")
                .timeStamp(new Date().getTime())
                .status(status.value())
                .title("Internal Exception")
                .detail(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();
        return new ResponseEntity<>(errorDetails,status);
    }
}
