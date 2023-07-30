package com.nakao.pos.repository;

import com.nakao.pos.model.Product;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Naoki Nakao on 7/18/2023
 * @project POS
 */

@Repository
public interface ProductRepository extends CrudRepository<Product, String>,
        PagingAndSortingRepository<Product, String> {

    @Query("SELECT COUNT(p.sku) " +
            "FROM Product p " +
            "WHERE p.category_id = :categoryId")
    Integer countByCategoryId(String categoryId);

    @Modifying
    @Query("UPDATE Product p " +
            "SET stock = stock + :quantity " +
            "WHERE p.sku = :sku")
    void updateStock(String sku, Integer quantity);

}
