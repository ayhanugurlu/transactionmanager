package com.au.example.service.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
public class CustomerNotFound extends TemplateException {

    public CustomerNotFound(String nationalityId) {
        errors.add(String.format("Customer Not Found.  Nationality Id %s", nationalityId));
    }

    public CustomerNotFound(long customerId) {
        errors.add(String.format("Customer Not Found.  Customer Id %d", customerId));
    }

    @Override
    public String getErrorCode() {
        return ErrorCode.CUSTOMER_NOT_FOUND;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
