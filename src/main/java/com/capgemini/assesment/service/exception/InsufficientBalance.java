package com.capgemini.assesment.service.exception;

import org.springframework.http.HttpStatus;

import static com.capgemini.assesment.service.exception.ErrorCode.INSUFFICENT_BALANCE;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
public class InsufficientBalance extends TemplateException {

    public InsufficientBalance(){
        errors.add("Insufficient Balance");
    }
    @Override
    public String getErrorCode() {
        return INSUFFICENT_BALANCE;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
