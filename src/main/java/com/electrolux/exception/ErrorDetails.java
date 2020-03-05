package com.electrolux.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {
    private final Date timestamp;
    private final String message;
    private final String details;
}
