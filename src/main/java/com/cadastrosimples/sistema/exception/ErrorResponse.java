package com.cadastrosimples.sistema.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ErrorResponse {

    private final int httpStatus;
    private final String errorMessage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime dateAndTime;

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
}