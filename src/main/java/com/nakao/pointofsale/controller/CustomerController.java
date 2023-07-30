package com.nakao.pos.controller;

import com.nakao.pos.model.Customer;
import com.nakao.pos.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Naoki Nakao on 7/22/2023
 * @project POS
 */

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Customer> customers = customerService.getCustomers(pageNumber, pageSize);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        Customer customer = customerService.getCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String > createCustomer(@RequestBody @Valid Customer customer) {
        customerService.createCustomer(customer);
        return new ResponseEntity<>("Customer created", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String > updateCustomer(@PathVariable String id, @RequestBody @Valid Customer customer) {
        customerService.updateCustomer(id, customer);
        return new ResponseEntity<>("Customer updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
