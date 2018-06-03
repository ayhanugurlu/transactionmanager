package com.au.example.service.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
public class AccountNotFound extends TemplateException {

    public AccountNotFound(long accountId) {
        errors.add(String.format("Account Not Found.Account Id = %d", accountId));
    }

    @Override
    public String getErrorCode() {
        return ErrorCode.ACCOUNT_NOT_FOUND;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
