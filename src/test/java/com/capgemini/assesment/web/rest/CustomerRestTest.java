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
    public void addCustomer() throws Exception {
        AddCustomerRequest addCustomerRequest = AddCustomerRequest.builder().name("name").surname("surname").nationalityId("try").build();
        HttpEntity<AddCustomerRequest> entity = new HttpEntity<>(addCustomerRequest, headers);
        ResponseEntity<AddCustomerResponse> response = restTemplate.exchange(createURLWithPort("/addCustomer"),
                HttpMethod.POST, entity, AddCustomerResponse.class);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.OK);
        Assert.assertEquals(response.getBody().getCustomerId(),new Long(1));
    }


    @Test
    public void getCustomer() throws Exception {
        headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<GetCustomerResponse> response = restTemplate.exchange(createURLWithPort("/getCustomer/1"),
                HttpMethod.GET,entity, GetCustomerResponse.class);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.OK);
        Assert.assertEquals(response.getBody().getId(),1);
        Assert.assertEquals(response.getBody().getName(),"name");
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
