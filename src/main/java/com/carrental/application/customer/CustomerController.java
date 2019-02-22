package com.carrental.application.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(path="/apis/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/")
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/")
    public Customer create(@RequestBody Customer customerCreate) {
        return customerRepository.save(customerCreate);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable long id) {
        return customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
    
    @PutMapping("/{id}")
    public Customer update(@PathVariable long id, @RequestBody Customer customerUpdate) {
        return customerRepository
                .findById(id)
                .map(customer -> {
                    customer.setFirstName(customerUpdate.getFirstName());
                    customer.setLastName(customerUpdate.getLastName());

                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        customerRepository.deleteById(id);
    }
}
