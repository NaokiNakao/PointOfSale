package com.nakao.pointofsale.repository;

import com.nakao.pointofsale.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String>,
        PagingAndSortingRepository<Customer, String > {
}
