package com.au.example.service.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
public class CustomerAlreadyExist extends TemplateException {

    public CustomerAlreadyExist(String nationalityId) {
        errors.add(String.format("Customer Already Exist. Nationality Id %s", nationalityId));
    }

    @Override
    public String getErrorCode() {
        return ErrorCode.CUSTOMER_ALREADY_EXIST;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
