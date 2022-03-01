package edu.uwo.health.entity;

import java.io.Serializable;

public class ResponseEntity<T> implements Serializable {
    public static final int SUCCESS = 200;
    public static final int FAIL = 500;
    public static final int NOT_LOGIN = 400;
    public static final int EXCEPTION = 401;
    public static final int SYS_ERROR = 402;
    public static final int PARAMS_ERROR = 403;
    public static final int NOT_FOUND = 404;
    public static final int NOT_SUPPORTED = 410;
    public static final int INVALID_AUTHCODE = 444;
    public static final int TOO_FREQUENT = 445;
    public static final int UNKNOWN_ERROR = 499;
    public static final int REPORT_ALREADY = 406;
    public static final int INVALID_PARAMS = 410;

    private Integer code;
    private String message;
    private T data;

    public ResponseEntity() {}

    public ResponseEntity(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseEntity success(T data){
        return new ResponseEntity(SUCCESS , "request success" , data);
    }

    public static <T> ResponseEntity success(String message , T data){
        return new ResponseEntity(SUCCESS , message , data);
    }

    public static <T> ResponseEntity success(Integer code, String message , T data){
        return new ResponseEntity(code , message , data);
    }

    public static <T> ResponseEntity fail(String message){
        return new ResponseEntity(FAIL , message , null);
    }

    public static <T> ResponseEntity fail(String message , T data){
        return new ResponseEntity(FAIL , message , data);
    }

    public static <T> ResponseEntity fail(Integer code, String message){
        return new ResponseEntity(code , message, null);
    }

    public static <T> ResponseEntity fail(Integer code, String message , T data){
        return new ResponseEntity(code , message , data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
