package com.nakao.pointofsale.repository;

import com.nakao.pointofsale.model.Order;
import com.nakao.pointofsale.model.OrderItem;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends ListCrudRepository<Order, String>,
        PagingAndSortingRepository<Order, String> {

    @Query("SELECT SUM(p.selling_price) " +
            "FROM Order_Item oi " +
            "JOIN Product p ON oi.product_sku = p.sku " +
            "WHERE oi.order_id = :orderId")
    BigDecimal getOrderItemsNetSum(String orderId);

    @Query("SELECT oi.id " +
            "FROM Order_Item oi " +
            "WHERE oi.order_id = :orderId AND oi.product_sku = :productSku " +
            "LIMIT 1")
    String getFirstOrderItemIdWithProductSku(String orderId, String productSku);

    @Query("SELECT COUNT(o.id) " +
            "FROM Orders o " +
            "WHERE o.customer_id = :customerId")
    Integer countByCustomerId(String customerId);

    @Query("SELECT COUNT(o.id) " +
            "FROM Orders o " +
            "WHERE o.employee_id = :employeeId")
    Integer countByEmployeeId(String employeeId);

    @Query("SELECT * " +
            "FROM order_item oi " +
            "WHERE oi.order_id = :orderId")
    List<OrderItem> getOrderItems(String orderId);

    @Query("SELECT o.* " +
            "FROM Orders o " +
            "WHERE o.employee_id = :employeeId AND o.status = :status " +
            "LIMIT 1")
    Optional<Order> getByEmployeeIdAndStatus(String employeeId, String status);

}
