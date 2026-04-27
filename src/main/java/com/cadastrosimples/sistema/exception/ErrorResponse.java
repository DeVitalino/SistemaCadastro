package com.cadastrosimples.sistema.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ErrorResponse {

    private int httpStatus;
    private String errorMessage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateAndTime;

    public ErrorResponse() {}

    public ErrorResponse(int httpStatus, String errorMessage, LocalDateTime dateAndTime) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.dateAndTime = dateAndTime;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}