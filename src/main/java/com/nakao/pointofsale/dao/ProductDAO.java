package com.nakao.pos.dao;

import com.nakao.pos.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Naoki Nakao on 7/18/2023
 * @project POS
 */

@Component
@RequiredArgsConstructor
public class ProductDAO implements DAO<Product> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Product product) {
        String sql = "INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Object[] params = {product.getSku(), product.getName(), product.getCategoryId(), product.getStock(),
        product.getMinStock(), product.getAcquisitionCost(), product.getSellingPrice()};

        jdbcTemplate.update(sql, params);
    }

}
