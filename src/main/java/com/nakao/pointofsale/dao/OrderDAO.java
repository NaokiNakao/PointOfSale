package com.nakao.pointofsale.dao;

import com.nakao.pointofsale.model.Order;
import com.nakao.pointofsale.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDAO implements DAO<Order> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Order order) {
        String sql = "INSERT INTO orders (id, employee_id) " +
                "VALUES (?, ?)";

        Object[] params = {order.getId(), order.getEmployeeId()};

        jdbcTemplate.update(sql, params);
    }

    public void insertItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_item (id, product_sku, order_id) " +
                "VALUES (?, ?, ?)";

        Object[] params = {orderItem.getId(), orderItem.getProductSku(), orderItem.getOrderId()};

        jdbcTemplate.update(sql, params);
    }

    public void deleteItem(String orderItemId) {
        String sql = "DELETE FROM order_item " +
                "WHERE id = ?";

        Object[] params = {orderItemId};

        jdbcTemplate.update(sql, params);
    }

}
