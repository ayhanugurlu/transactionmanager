package com.au.example.web.rest;

import com.au.example.TransactionManagerApplication;
import com.au.example.web.rest.request.account.AddCustomerAccountRequest;
import com.au.example.web.rest.request.customer.AddCustomerRequest;
import com.au.example.web.rest.request.transaction.TransactionRequest;
import com.au.example.web.rest.response.account.AddCustomerAccountResponse;
import com.au.example.web.rest.response.account.GetAccountTransactionResponse;
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
public class AccountRestTest {

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @LocalServerPort
    private int port;

    private AddCustomerResponse addCustomerResponse;

    private AddCustomerAccountResponse addCustomerAccountResponse;

    @Before
    public void setUp() {
        //add customer
        AddCustomerRequest addCustomerRequest = AddCustomerRequest.builder().name("name").surname("surname").nationalityId("xx").build();
        HttpEntity<AddCustomerRequest> addCustomerRequestHttpEntity = new HttpEntity<>(addCustomerRequest, headers);
        ResponseEntity<AddCustomerResponse> addCustomerResponseResponseEntity = restTemplate.exchange(createURLWithPort("/addCustomer"),
                HttpMethod.POST, addCustomerRequestHttpEntity, AddCustomerResponse.class);
        addCustomerResponse = addCustomerResponseResponseEntity.getBody();

        //add account
        AddCustomerAccountRequest addCustomerAccountRequest = AddCustomerAccountRequest.builder().ownerId(addCustomerResponse.getCustomerId()).currencyType("TRY").build();
        HttpEntity<AddCustomerAccountRequest> addCustomerAccountRequestHttpEntity = new HttpEntity<>(addCustomerAccountRequest, headers);
        ResponseEntity<AddCustomerAccountResponse> addCustomerAccountResponseResponseEntity = restTemplate.exchange(createURLWithPort("/addAccount"),
                HttpMethod.POST, addCustomerAccountRequestHttpEntity, AddCustomerAccountResponse.class);
        addCustomerAccountResponse = addCustomerAccountResponseResponseEntity.getBody();

        //add transaction
        TransactionRequest transactionRequest = TransactionRequest.builder().accountId(addCustomerAccountResponse.getId()).amount(10).build();
        HttpEntity<TransactionRequest> transactionRequestHttpEntity = new HttpEntity<>(transactionRequest, headers);
        ResponseEntity<TransactionResponse> transactionResponseResponseEntity = restTemplate.exchange(createURLWithPort("/doTransaction"), HttpMethod.POST, transactionRequestHttpEntity, TransactionResponse.class);


    }


    @Test
    public void accountRestTest() throws Exception {

        //CustomerNotFound
        AddCustomerAccountRequest addCustomerAccountRequest = AddCustomerAccountRequest.builder().ownerId(-1).currencyType("TRY").build();
        HttpEntity<AddCustomerAccountRequest> addCustomerAccountRequestHttpEntity = new HttpEntity<>(addCustomerAccountRequest, headers);
        ResponseEntity<AddCustomerAccountResponse> addCustomerAccountResponseResponseEntity = restTemplate.exchange(createURLWithPort("/addAccount"),
                HttpMethod.POST, addCustomerAccountRequestHttpEntity, AddCustomerAccountResponse.class);
        Assert.assertEquals(addCustomerAccountResponseResponseEntity.getStatusCode(), HttpStatus.NOT_FOUND);

        //AddAccount
        addCustomerAccountRequest = AddCustomerAccountRequest.builder().ownerId(addCustomerResponse.getCustomerId()).currencyType("TRY").build();
        addCustomerAccountRequestHttpEntity = new HttpEntity<>(addCustomerAccountRequest, headers);
        addCustomerAccountResponseResponseEntity = restTemplate.exchange(createURLWithPort("/addAccount"),
                HttpMethod.POST, addCustomerAccountRequestHttpEntity, AddCustomerAccountResponse.class);
        Assert.assertEquals(addCustomerAccountResponseResponseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(addCustomerAccountResponseResponseEntity.getBody().getOwnerId(), addCustomerResponse.getCustomerId());

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GetAccountTransactionResponse> getAccountTransactionResponseResponseEntity = restTemplate.exchange(createURLWithPort("/getAccountTransactions/" + addCustomerAccountResponse.getId()),
                HttpMethod.GET, entity, GetAccountTransactionResponse.class);
        Assert.assertEquals(getAccountTransactionResponseResponseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(getAccountTransactionResponseResponseEntity.getBody().getName(), "name");
        Assert.assertEquals(getAccountTransactionResponseResponseEntity.getBody().getSurname(), "surname");
        Assert.assertEquals(getAccountTransactionResponseResponseEntity.getBody().getTransactionResponses().size(), 1);
        Assert.assertEquals(getAccountTransactionResponseResponseEntity.getBody().getTransactionResponses().get(0).getAmount(), 10);


    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
