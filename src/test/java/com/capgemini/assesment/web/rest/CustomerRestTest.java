package com.capgemini.assesment.web.rest;

import com.capgemini.assesment.TransactionmanagerApplication;
import com.capgemini.assesment.service.CustomerService;
import com.capgemini.assesment.web.rest.request.account.AddCustomerAccountRequest;
import com.capgemini.assesment.web.rest.request.customer.AddCustomerRequest;
import com.capgemini.assesment.web.rest.response.customer.AddCustomerResponse;
import com.capgemini.assesment.web.rest.response.customer.GetCustomerResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.glasnost.orika.MapperFacade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.sleuth.Tracer;
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
public class CustomerRestTest {

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @LocalServerPort
    private int port;


    @Test
    public void customerRestTest() throws Exception {
        //customer not found
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GetCustomerResponse> getCustomerResponseResponseEntity = restTemplate.exchange(createURLWithPort("/getCustomer/1"),
                HttpMethod.GET,entity, GetCustomerResponse.class);
        Assert.assertEquals(getCustomerResponseResponseEntity.getStatusCode(),HttpStatus.NOT_FOUND);

        //add customer
        AddCustomerRequest addCustomerRequest = AddCustomerRequest.builder().name("name").surname("surname").nationalityId("12").build();
        HttpEntity<AddCustomerRequest> addCustomerRequestHttpEntity = new HttpEntity<>(addCustomerRequest, headers);
        ResponseEntity<AddCustomerResponse> addCustomerResponseResponseEntity = restTemplate.exchange(createURLWithPort("/addCustomer"),
                HttpMethod.POST, addCustomerRequestHttpEntity, AddCustomerResponse.class);
        Assert.assertEquals(addCustomerResponseResponseEntity.getStatusCode(),HttpStatus.OK);


        getCustomerResponseResponseEntity = restTemplate.exchange(createURLWithPort("/getCustomer/12"),
                HttpMethod.GET,entity, GetCustomerResponse.class);
        Assert.assertEquals(getCustomerResponseResponseEntity.getStatusCode(),HttpStatus.OK);
        Assert.assertEquals(addCustomerResponseResponseEntity.getBody().getCustomerId(),getCustomerResponseResponseEntity.getBody().getId());

        //duplicate customer
        ResponseEntity<AddCustomerResponse> duplicateResponse = restTemplate.exchange(createURLWithPort("/addCustomer"),
                HttpMethod.POST, addCustomerRequestHttpEntity, AddCustomerResponse.class);
        Assert.assertEquals(duplicateResponse.getStatusCode(),HttpStatus.CONFLICT);

        ///get customer
        getCustomerResponseResponseEntity = restTemplate.exchange(createURLWithPort("/getCustomer/12"),
                HttpMethod.GET,entity, GetCustomerResponse.class);
        Assert.assertEquals(getCustomerResponseResponseEntity.getStatusCode(),HttpStatus.OK);
        Assert.assertEquals(getCustomerResponseResponseEntity.getBody().getName(),"name");
        Assert.assertEquals(getCustomerResponseResponseEntity.getBody().getSurname(),"surname");
        Assert.assertEquals(addCustomerResponseResponseEntity.getBody().getCustomerId(),getCustomerResponseResponseEntity.getBody().getId());

    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
