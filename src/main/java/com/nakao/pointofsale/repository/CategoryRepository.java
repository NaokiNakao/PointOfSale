package com.nakao.pos.repository;

import com.nakao.pos.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Naoki Nakao on 7/18/2023
 * @project POS
 */

@Repository
public interface CategoryRepository extends CrudRepository<Category, String>,
        PagingAndSortingRepository<Category, String> {
}
