package com.carrental.application.customer;

import com.carrental.application.Application;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CustomerEndpointTest {
    private MockMvc mockMvc;

    @Autowired
    private CustomerController controller;

    @Autowired
    private CustomerRepository repository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
	public void contextLoads() {
	}

    @Test
    public void listCustomersWhenEmptyShouldReturnEmptyArray() throws Exception {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/apis/customers/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

	@Test(expected = NestedServletException.class)
    public void retriveNonExistingCustomerShouldReturnError() throws Exception {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/apis/customers/2"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void createCustomerShouldReturnStatus200() throws Exception {
        String mockJson = "{\"firstName\": \"John\", \"lastName\": \"Doe\"}";
        mockMvc
            .perform(MockMvcRequestBuilders
                .post("/apis/customers/")
                .accept(MediaType.APPLICATION_JSON)
                .content(mockJson)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateCustomerShouldReturnStatus200() throws Exception {
        String mockJson = "{\"firstName\": \"Jhonny\", \"lastName\": \"Doe\"}";
        Customer savedCustomer = (Customer) repository.save(new Customer("John", "Doe"));
        mockMvc
            .perform(MockMvcRequestBuilders
                .put("/apis/customers/" + savedCustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .content(mockJson)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void updateNonExistingCustomerShouldReturnException() throws Exception {
        String mockJson = "{\"firstName\": \"Jhonny\", \"lastName\": \"Doe\"}";
        mockMvc
            .perform(MockMvcRequestBuilders
                .put("/apis/customers/9999")
                .accept(MediaType.APPLICATION_JSON)
                .content(mockJson)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
