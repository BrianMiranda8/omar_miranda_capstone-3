package org.yearup.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.yearup.errors.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryNotFound.class)
    public ResponseEntity<ApiErrorResponse> handleCategoryNotFound(CategoryNotFound ex){
        return new ResponseEntity<>(new ApiErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateCategory.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateCategory(DuplicateCategory ex){
        return new ResponseEntity<>(new ApiErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage()), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ProductNotFound.class)
    public  ResponseEntity<ApiErrorResponse> handleProductNotFound(ProductNotFound ex){
        return new ResponseEntity<>(new ApiErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage()), HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoHandlerFound(RuntimeException ex){
        return new ResponseEntity<>(new ApiErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFound(RuntimeException ex){
        return new ResponseEntity<>(new ApiErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage()), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(EmptyCart.class)
    public ResponseEntity<ApiErrorResponse> handleEmptyCart(RuntimeException ex){
        return new ResponseEntity<>(new ApiErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage()), HttpStatus.BAD_REQUEST);

    }


//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ApiErrorResponse> handleGlobalException(RuntimeException ex){
//        return new ResponseEntity<>(new ApiErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
