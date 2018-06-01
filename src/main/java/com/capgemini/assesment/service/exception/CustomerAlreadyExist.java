package com.capgemini.assesment.service.exception;

import org.springframework.http.HttpStatus;

import  static  com.capgemini.assesment.service.exception.ErrorCode.CUSTOMER_ALREADY_EXIST;
/**
 * Created by ayhanugurlu on 5/26/18.
 */
public class CustomerAlreadyExist extends TemplateException {

    public CustomerAlreadyExist(String nationalityId){
        errors.add( String.format("Customer Already Exist. Nationality Id %d",nationalityId));
    }
    @Override
    public String getErrorCode() {
        return CUSTOMER_ALREADY_EXIST;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
