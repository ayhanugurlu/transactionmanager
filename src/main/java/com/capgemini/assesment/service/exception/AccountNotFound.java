package com.capgemini.assesment.service.exception;

import org.springframework.http.HttpStatus;

import static com.capgemini.assesment.service.exception.ErrorCode.ACCOUNT_NOT_FOUND;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
public class AccountNotFound extends TemplateException {


    public AccountNotFound(){
        errors.add("Customer Not Found");
    }
    @Override
    public String getErrorCode() {
        return ACCOUNT_NOT_FOUND;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
