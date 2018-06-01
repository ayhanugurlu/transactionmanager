package com.capgemini.assesment.service.exception;

import org.springframework.http.HttpStatus;

import static com.capgemini.assesment.service.exception.ErrorCode.CUSTOMER_NOT_FOUND;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
public class CustomerNotFound extends TemplateException {

    public CustomerNotFound(String nationalityId){
        errors.add(String.format(String.format("Customer Not Found.  Nationality Id %d",nationalityId)));
    }

    public CustomerNotFound(long customerId){
        errors.add(String.format(String.format("Customer Not Found.  Customer Id %d",customerId)));
    }
    @Override
    public String getErrorCode() {
        return CUSTOMER_NOT_FOUND;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
