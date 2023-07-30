package com.nakao.pos.repository;

import com.nakao.pos.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Naoki Nakao on 7/22/2023
 * @project POS
 */

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String>,
        PagingAndSortingRepository<Customer, String > {
}
