package com.capgemini.assesment.service.exception;

import static com.capgemini.assesment.service.exception.ErrorCode.CUSTOMER_NOT_FOUND;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
public class CustomerNotFound extends TemplateException {


    public CustomerNotFound(){
        errors.add("Customer Not Found");
    }
    @Override
    public String getErrorCode() {
        return CUSTOMER_NOT_FOUND;
    }
}
