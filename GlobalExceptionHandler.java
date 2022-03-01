package edu.uwo.health.utils;

import edu.uwo.health.entity.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static edu.uwo.health.entity.ResponseEntity.FAIL;
import static edu.uwo.health.entity.ResponseEntity.PARAMS_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity exceptionHandle(BindException exception) {

        BindingResult result = exception.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> fieldErrors = result.getFieldErrors();
        fieldErrors.forEach(error -> {
            errorMsg.append(error.getDefaultMessage()).append("!");
        });
        return ResponseEntity.fail(PARAMS_ERROR, errorMsg.toString());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity MyExceptionHandle(MethodArgumentNotValidException exception) {

        BindingResult result = exception.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> fieldErrors = result.getFieldErrors();
        fieldErrors.forEach(error -> {
            errorMsg.append(error.getDefaultMessage()).append("!");
        });

        return ResponseEntity.fail(PARAMS_ERROR, errorMsg.toString());
    }

    // 处理运行时异常
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity doHandleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.fail(FAIL, e.getMessage());
    }
}
