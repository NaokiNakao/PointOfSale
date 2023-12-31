package com.nakao.pointofsale.dao;

import com.nakao.pointofsale.model.StockReplenishment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockReplenishmentDAO implements DAO<StockReplenishment> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(StockReplenishment stockReplenishment) {
        String sql = "INSERT INTO stock_replenishment (id, delivery_date, product_sku, product_quantity, supplier_id, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Object[] params = {stockReplenishment.getId(), stockReplenishment.getDeliveryDate(), stockReplenishment.getProductSku(),
        stockReplenishment.getProductQuantity(), stockReplenishment.getSupplierId(), stockReplenishment.getStatus()};

        jdbcTemplate.update(sql, params);
    }

}
