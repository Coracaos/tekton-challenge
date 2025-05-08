package com.tekton.challenge.exception;

import com.tekton.challenge.model.response.ApiErrorResp;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFound(NoHandlerFoundException ex) {
        ApiErrorResp error = new ApiErrorResp();
        error.setCode(ErrorCode.ENDPOINT_NOT_FOUND);
        error.setMessage("the requested endpoint does not exist");
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResp> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ApiErrorResp error = new ApiErrorResp();
        error.setCode(ErrorCode.NOT_READABLE_REQUEST);
        error.setMessage("malformed request body");
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResp> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        ApiErrorResp error = new ApiErrorResp();
        error.setCode(ErrorCode.METHOD_NOT_ALLOWED);
        error.setMessage(ex.getMessage());
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResp> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ApiErrorResp error = new ApiErrorResp();
        error.setCode(ErrorCode.VALIDATION_FAILED);
        error.setMessage("one or more fields in the request are invalid");
        error.setSubErrors(ex.getFieldErrors().stream().map(e -> {
           StringBuilder str = new StringBuilder();
           str.append(e.getField()).append("=").append(e.getRejectedValue())
                   .append(" : ").append(e.getDefaultMessage());
           return str.toString();
        }).toList());
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResp> handlerMissingServletRequestParameterException(MissingServletRequestParameterException ex){
        ApiErrorResp error = new ApiErrorResp();
        error.setCode(ErrorCode.MISSING_REQUEST_PARAMETER);
        error.setMessage(ex.getMessage());
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResp> handleConstraintViolationException(ConstraintViolationException ex) {
        ApiErrorResp error = new ApiErrorResp();
        error.setCode(ErrorCode.CONSTRAINT_VIOLATION);
        error.setMessage("one or more fields in the request are invalid");
        error.setSubErrors(ex.getConstraintViolations().stream().map(e ->{
            StringBuilder str = new StringBuilder();
            str.append(e.getPropertyPath()).append("=").append(e.getInvalidValue())
                    .append(" : ").append(e.getMessage());
            return str.toString();
        }).toList());
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResp> handleError(Exception ex) {
        ApiErrorResp error = new ApiErrorResp();
        error.setCode(ErrorCode.INTERNAL_ERROR);
        error.setMessage("an unexpected error occurred");
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
