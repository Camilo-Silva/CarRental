package com.carrental.application.customer;

import org.assertj.core.api.Assertions;
import org.h2.value.DataType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerEndpointTest {
    @Autowired
    private TestRestTemplate restTemplate;
    
    @LocalServerPort
    private int port;

    @MockBean
    private CustomerRepository repository;

    @Test
    public void listCustomersWhenEmptyShouldReturnError() {
        System.out.println(port);
        ResponseEntity<String> response = restTemplate.getForEntity("/apis/customers/2", String.class);

        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(500);
    }
}
