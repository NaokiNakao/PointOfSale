package com.nakao.pos.repository;

import com.nakao.pos.model.StockReplenishment;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Naoki Nakao on 7/19/2023
 * @project POS
 */

@Repository
public interface StockReplenishmentRepository extends CrudRepository<StockReplenishment, String>,
        PagingAndSortingRepository<StockReplenishment, String> {

    @Modifying
    @Query("UPDATE Stock_Replenishment sr " +
            "SET status = :status " +
            "WHERE sr.id = :id")
    void updateStatus(String id, String status);

    @Query("SELECT COUNT(sr.id) " +
            "FROM Stock_Replenishment sr " +
            "WHERE sr.product_sku = :productSku")
    Integer countByProductSku(String productSku);

    @Query("SELECT COUNT(sr.id) " +
            "FROM Stock_Replenishment sr " +
            "WHERE sr.supplier_id = :supplierId")
    Integer countBySupplierId(Long supplierId);

}
