package com.capgemini.assesment.web.rest.handler;

import com.capgemini.assesment.service.exception.AccountNotFound;
import com.capgemini.assesment.service.exception.CustomerAlreadyExist;
import com.capgemini.assesment.service.exception.CustomerNotFound;
import com.capgemini.assesment.service.exception.InsufficientBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${error.code.header.key}")
    private String errorCodeHeaderKey;

    @Autowired
    private Tracer tracer;

    @ExceptionHandler(CustomerAlreadyExist.class)
    public ResponseEntity<ErrResponse> handlCustomerAlreadyExist(HttpServletRequest request, CustomerAlreadyExist e) {

        String errorMessage = e.getErrors().stream().collect(Collectors.joining(", "));
        String[] eCodes = {e.getErrorCode()};

        logger.error(errorMessage);
        ErrResponse response = new ErrResponse(tracer.getCurrentSpan().getTraceId(), errorMessage);


        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .header(errorCodeHeaderKey, eCodes)
                .body(response);

    }


    @ExceptionHandler(CustomerNotFound.class)
    public ResponseEntity<ErrResponse> handleCustomerNotFound(HttpServletRequest request, CustomerNotFound e) {

        String errorMessage = e.getErrors().stream().collect(Collectors.joining(", "));
        String[] eCodes = {e.getErrorCode()};

        logger.error(errorMessage);
        ErrResponse response = new ErrResponse(tracer.getCurrentSpan().getTraceId(), errorMessage);


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(errorCodeHeaderKey, eCodes)
                .body(response);
    }


    @ExceptionHandler(AccountNotFound.class)
    public ResponseEntity<ErrResponse> handleAccountNotFound(HttpServletRequest request, AccountNotFound e) {

        String errorMessage = e.getErrors().stream().collect(Collectors.joining(", "));
        String[] eCodes = {e.getErrorCode()};

        logger.error(errorMessage);
        ErrResponse response = new ErrResponse(tracer.getCurrentSpan().getTraceId(), errorMessage);


        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(errorCodeHeaderKey, eCodes)
                .body(response);
    }


    @ExceptionHandler(InsufficientBalance.class)
    public ResponseEntity<ErrResponse> handleInsufficientBalance(HttpServletRequest request, InsufficientBalance e) {

        String errorMessage = e.getErrors().stream().collect(Collectors.joining(", "));
        String[] eCodes = {e.getErrorCode()};

        logger.error(errorMessage);
        ErrResponse response = new ErrResponse(tracer.getCurrentSpan().getTraceId(), errorMessage);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header(errorCodeHeaderKey, eCodes)
                .body(response);
    }
}
