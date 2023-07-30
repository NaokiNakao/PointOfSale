package com.nakao.pointofsale.repository;

import com.nakao.pointofsale.model.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long>,
        PagingAndSortingRepository<Supplier, Long> {
}
