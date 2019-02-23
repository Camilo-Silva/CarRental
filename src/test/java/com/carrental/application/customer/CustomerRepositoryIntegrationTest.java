package com.carrental.application.customer;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void whenFindByName_thenReturnCustomer() {
        Customer customer = new Customer("John", "Doe");
        entityManager.persist(customer);
        entityManager.flush();

        List<Customer> customers = customerRepository.findByFirstName("John");

        assertThat(customers)
            .isNotEmpty()
            .contains(customer);
    }
}