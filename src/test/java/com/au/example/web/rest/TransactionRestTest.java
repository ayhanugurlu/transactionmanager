package com.au.example.web.rest;

import com.au.example.TransactionManagerApplication;
import com.au.example.web.rest.request.account.AddCustomerAccountRequest;
import com.au.example.web.rest.request.customer.AddCustomerRequest;
import com.au.example.web.rest.request.transaction.TransactionRequest;
import com.au.example.web.rest.response.account.AddCustomerAccountResponse;
import com.au.example.web.rest.response.customer.AddCustomerResponse;
import com.au.example.web.rest.response.transaction.TransactionResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by ayhanugurlu on 5/28/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionRestTest {

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @LocalServerPort
    private int port;


    @Before
    public void setUp() {
        //add customer
        AddCustomerRequest addCustomerRequest = AddCustomerRequest.builder().name("name").surname("surname").nationalityId("tr").build();
        HttpEntity<AddCustomerRequest> addCustomerRequestHttpEntity = new HttpEntity<>(addCustomerRequest, headers);
        ResponseEntity<AddCustomerResponse> addCustomerResponseResponseEntity = restTemplate.exchange(createURLWithPort("/addCustomer"),
                HttpMethod.POST, addCustomerRequestHttpEntity, AddCustomerResponse.class);

        //AddAccount
        AddCustomerAccountRequest addCustomerAccountRequest = AddCustomerAccountRequest.builder().ownerId(1).currencyType("TRY").build();
        HttpEntity<AddCustomerAccountRequest> addCustomerAccountRequestHttpEntity = new HttpEntity<>(addCustomerAccountRequest, headers);
        ResponseEntity<AddCustomerAccountResponse> addCustomerAccountResponseResponseEntity = restTemplate.exchange(createURLWithPort("/addAccount"),
                HttpMethod.POST, addCustomerAccountRequestHttpEntity, AddCustomerAccountResponse.class);
    }

    @Test
    public void transactionRestTest() {
        //doTransaction
        TransactionRequest transactionRequest = TransactionRequest.builder().accountId(1).amount(10).build();
        HttpEntity<TransactionRequest> transactionRequestHttpEntity = new HttpEntity<>(transactionRequest, headers);
        ResponseEntity<TransactionResponse> transactionResponseResponseEntity = restTemplate.exchange(createURLWithPort("/doTransaction"), HttpMethod.POST, transactionRequestHttpEntity, TransactionResponse.class);
        Assert.assertEquals(transactionResponseResponseEntity.getStatusCode(), HttpStatus.OK);

        //AccountNotFound
        transactionRequest = TransactionRequest.builder().accountId(-1).amount(10).build();
        transactionRequestHttpEntity = new HttpEntity<>(transactionRequest, headers);
        transactionResponseResponseEntity = restTemplate.exchange(createURLWithPort("/doTransaction"), HttpMethod.POST, transactionRequestHttpEntity, TransactionResponse.class);
        Assert.assertEquals(transactionResponseResponseEntity.getStatusCode(), HttpStatus.NOT_FOUND);

        //InsufficientBalance
        transactionRequest = TransactionRequest.builder().accountId(1).amount(-110).build();
        transactionRequestHttpEntity = new HttpEntity<>(transactionRequest, headers);
        transactionResponseResponseEntity = restTemplate.exchange(createURLWithPort("/doTransaction"), HttpMethod.POST, transactionRequestHttpEntity, TransactionResponse.class);
        Assert.assertEquals(transactionResponseResponseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);


    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
