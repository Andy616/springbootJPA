package com.andy.springbootjpa.dto;


import org.springframework.http.HttpStatus;

public class ResponseDTO {
    private Integer status;
    private String message;

    public ResponseDTO() {
        this.status = HttpStatus.OK.value();
        this.message = "success";
    }

    public ResponseDTO(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
