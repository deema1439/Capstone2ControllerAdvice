package com.example.capestone2.Advice;

import com.example.capestone2.Api.ApiException;
import com.example.capestone2.Api.ApiRes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?>ApiException(ApiException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<?> DataIntegrityViolationException(DataIntegrityViolationException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(new ApiRes(message));
    }

    @ExceptionHandler(value=MethodArgumentNotValidException.class)
    public ResponseEntity<?>MethodArgumentNotValidException (MethodArgumentNotValidException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(new ApiRes(message));
    }

    // ظهر لمن ما اضفت قيمه
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<?>HttpMessageNotReadableException(HttpMessageNotReadableException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(new ApiRes(message));
    }



    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<?>NoResourceFoundException(NoResourceFoundException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(new ApiRes(message));
    }

    //حطيته لمن جيت بدال ما احط json حطيت text
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?>HttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(new ApiRes(message));
    }

    // ظهر لمن حطيت بدال الرقم حروف
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?>MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(new ApiRes(message));
    }


    //ظهر لمن حطيت قيت بدال بوست
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?>HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(new ApiRes(message));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?>RuntimeException(RuntimeException e) {
        return ResponseEntity.status(400).body(new ApiRes(e.getMessage()));
    }


















}
