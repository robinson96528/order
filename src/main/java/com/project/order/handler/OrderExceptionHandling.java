package com.project.order.handler;

import com.project.order.exception.ExternalAPIFailure;
import com.project.order.exception.InvalidOrder;
import com.project.order.exception.OrderNotFoundException;
import com.project.order.exception.ProductNotFound;
import com.project.order.model.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class OrderExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = OrderNotFoundException.class)
    public ResponseEntity<Object> handleException(OrderNotFoundException exception){
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(new Date());
        response.setMessage("Not found");
        ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        return entity;
    }

    @ExceptionHandler(value = InvalidOrder.class)
    public ResponseEntity<Object> handleException(InvalidOrder exception){
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(new Date());
        response.setMessage("Invalid Order");
        ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return entity;
    }

    @ExceptionHandler(value = ProductNotFound.class)
    public ResponseEntity<Object> handleException(ProductNotFound exception){
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(new Date());
        response.setMessage("Products Unavailable");
        ResponseEntity<Object> entity = new ResponseEntity<>(exception.getInvalidProduct(), HttpStatus.RESET_CONTENT);
        return entity;
    }

    @ExceptionHandler(value = ExternalAPIFailure.class)
    public ResponseEntity<Object> handleException(ExternalAPIFailure exception){
        ExceptionResponse response = new ExceptionResponse();
        response.setDateTime(new Date());
        response.setMessage("External API Call Failure");
        ResponseEntity<Object> entity = new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        return entity;
    }
}
