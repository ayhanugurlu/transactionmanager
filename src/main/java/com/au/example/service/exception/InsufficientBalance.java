package com.au.example.service.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
public class InsufficientBalance extends TemplateException {

    public InsufficientBalance() {
        errors.add("Insufficient Balance");
    }

    @Override
    public String getErrorCode() {
        return ErrorCode.INSUFFICENT_BALANCE;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
