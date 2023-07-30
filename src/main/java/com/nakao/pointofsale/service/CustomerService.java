package com.nakao.pointofsale.service;

import com.nakao.pointofsale.dao.CustomerDAO;
import com.nakao.pointofsale.exception.DeletionException;
import com.nakao.pointofsale.exception.NotFoundException;
import com.nakao.pointofsale.exception.UniqueIdentifierGenerationException;
import com.nakao.pointofsale.model.Customer;
import com.nakao.pointofsale.repository.CustomerRepository;
import com.nakao.pointofsale.repository.OrderRepository;
import com.nakao.pointofsale.util.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDAO customerDAO;
    private final OrderRepository orderRepository;

    public List<Customer> getCustomers(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Customer> page = customerRepository.findAll(pageable);
        return page.getContent();
    }

    public Customer getCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with ID: " + id));
    }

    public void createCustomer(Customer customer) {
        customer.setId(IdentifierGenerator.generateIdentifier(Customer.ID_PATTERN));
        uniqueIdVerification(customer.getId());
        customerDAO.insert(customer);
    }

    public void updateCustomer(String id, Customer customer) {
        Customer updatedCustomer = getCustomerById(id);
        BeanUtils.copyProperties(customer, updatedCustomer);
        updatedCustomer.setId(id);
        customerRepository.save(updatedCustomer);
    }

    public void deleteCustomer(String id) {
        if (customerRepository.existsById(id)) {
            if (isValidCustomerDeletion(id)) {
                customerRepository.deleteById(id);
            } else {
                throw new DeletionException("Unable to delete Customer with ID: " + id);
            }
        }
        else {
            throw new NotFoundException("Customer not found with ID: " + id);
        }
    }

    private void uniqueIdVerification(String id) {
        if (customerRepository.existsById(id)) {
            throw new UniqueIdentifierGenerationException("Error generating unique identifier for Customer");
        }
    }

    private Boolean isValidCustomerDeletion(String id) {
        return orderRepository.countByCustomerId(id) == 0;
    }

}
