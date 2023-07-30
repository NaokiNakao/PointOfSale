package com.nakao.pointofsale.repository;

import com.nakao.pointofsale.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, String>,
        PagingAndSortingRepository<Category, String> {
}
