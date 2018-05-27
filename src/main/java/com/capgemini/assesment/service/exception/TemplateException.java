package com.capgemini.assesment.service.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
public abstract class TemplateException extends  Exception{

    protected List<String> errors = new ArrayList<>();

    public abstract  String getErrorCode();

    public List<String> getErrors(){
        return  errors;
    }

}