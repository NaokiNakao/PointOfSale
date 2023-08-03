package com.nakao.pointofsale.repository;

import com.nakao.pointofsale.model.Product;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, String>,
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

    @Modifying
    @Query("UPDATE Product p " +
            "SET stock = stock - 1 " +
            "WHERE p.sku = :sku")
    void decreaseStock(String sku);

}
