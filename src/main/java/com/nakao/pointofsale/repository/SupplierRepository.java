package com.nakao.pos.repository;

import com.nakao.pos.model.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Naoki Nakao on 7/19/2023
 * @project POS
 */

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long>,
        PagingAndSortingRepository<Supplier, Long> {
}
