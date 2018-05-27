package com.capgemini.assesment.service.exception;

import  static  com.capgemini.assesment.service.exception.ErrorCode.CUSTOMER_ALREADY_EXIST;
/**
 * Created by ayhanugurlu on 5/26/18.
 */
public class CustomerAlreadyExist extends TemplateException {

    public CustomerAlreadyExist(){
        errors.add("Customer Already Exist");
    }
    @Override
    public String getErrorCode() {
        return CUSTOMER_ALREADY_EXIST;
    }
}
