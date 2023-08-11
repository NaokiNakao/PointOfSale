package com.nakao.pointofsale.dao;

import com.nakao.pointofsale.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductDAO implements DAO<Product> {
    private final JdbcTemplate jdbcTemplate;

    public ProductDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Product product) {
        String sql = "INSERT INTO product (sku, name, category_id, stock, min_stock, acquisition_cost, selling_price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Object[] params = {product.getSku(), product.getName(), product.getCategoryId(), product.getStock(),
        product.getMinStock(), product.getAcquisitionCost(), product.getSellingPrice()};

        jdbcTemplate.update(sql, params);
    }
}
