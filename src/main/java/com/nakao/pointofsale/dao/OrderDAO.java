package com.nakao.pointofsale.dao;

import com.nakao.pointofsale.model.Order;
import com.nakao.pointofsale.model.OrderItem;
import com.nakao.pointofsale.util.InvoiceProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public String getCustomerName(String customerId) {
        String sql = "SELECT CONCAT(c.first_name, ' ', c.last_name) AS name " +
                "FROM customer c " +
                "WHERE id = ?";

        Object[] params = {customerId};

        return jdbcTemplate.queryForObject(sql, String.class, params);
    }

    public String getEmployeeName(String employeeId) {
        String sql = "SELECT CONCAT(e.first_name, ' ', e.last_name) AS name " +
                "FROM employee e " +
                "WHERE id = ?";

        Object[] params = {employeeId};

        return jdbcTemplate.queryForObject(sql, String.class, params);
    }

    public List<InvoiceProduct> getInvoiceProducts(String orderId) {
        String sql = "SELECT p.name AS product_name, COUNT(oi.id) AS quantity, p.selling_price AS unit_price " +
                "FROM order_item oi " +
                "JOIN product p ON oi.product_sku = p.sku " +
                "WHERE oi.order_id = ? " +
                "GROUP BY oi.product_sku";

        Object[] params = {orderId};

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(InvoiceProduct.class), params);
    }

}
