package com.carrental.application.car;

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
public class CarEndpointTest {
    private MockMvc mockMvc;

    @Autowired
    private CarRepository repository;

    @Autowired
    private CarController controller;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void contextLoads() {  }

    @Test
    public void listCarsWhenEmptyShouldReturnEmptyArray() throws Exception {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/apis/cars/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test(expected = NestedServletException.class)
    public void retrieveNonExistingCarShouldReturnError() throws Exception {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/apis/cars/9999"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void createCarShouldReturnStatus200() throws Exception {
        mockMvc
            .perform(MockMvcRequestBuilders
                .post("/apis/cars")
                .accept(MediaType.APPLICATION_JSON)
                .content(mockStructureToPost())
                .contentType(MediaType.APPLICATION_JSON)
                )
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateCarShouldReturnStatus200() throws Exception {
        Car savedCar = (Car) repository.save(new Car("Chevrolet", "Cruze"));
        mockMvc
            .perform(MockMvcRequestBuilders
                .put("/apis/cars/" + savedCar.getId())
                .accept(MediaType.APPLICATION_JSON)
                .content(mockStructureToPut())
                .contentType(MediaType.APPLICATION_JSON)
                )
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void updateNonExistingCarShouldReturnError() throws Exception {
        mockMvc
            .perform(MockMvcRequestBuilders
                .put("/apis/cars/9999")
                .accept(MediaType.APPLICATION_JSON)
                .content(mockStructureToPut())
                .contentType(MediaType.APPLICATION_JSON)
                )
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public String mockStructureToPost() {
        return "{\"brand\": \"Chevrolet\", \"type\": \"Cruze\"}";
    }

    public String mockStructureToPut() {
        return "{\"brand\": \"Chevrolet\", \"type\": \"Onix\"}";
    }
}