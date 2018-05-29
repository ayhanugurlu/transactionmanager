package com.capgemini.assesment.web.rest;

import com.capgemini.assesment.TransactionmanagerApplication;
import com.capgemini.assesment.data.entity.Transaction;
import com.capgemini.assesment.service.model.input.transaction.TransactionInput;
import com.capgemini.assesment.web.rest.request.account.AddCustomerAccountRequest;
import com.capgemini.assesment.web.rest.request.customer.AddCustomerRequest;
import com.capgemini.assesment.web.rest.request.transaction.TransactionRequest;
import com.capgemini.assesment.web.rest.response.account.AddCustomerAccountResponse;
import com.capgemini.assesment.web.rest.response.customer.AddCustomerResponse;
import com.capgemini.assesment.web.rest.response.transaction.TransactionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ayhanugurlu on 5/28/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionmanagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
