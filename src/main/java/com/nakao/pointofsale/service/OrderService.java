package com.nakao.pointofsale.service;

import com.nakao.pointofsale.dao.OrderDAO;
import com.nakao.pointofsale.enumeration.OrderStatus;
import com.nakao.pointofsale.exception.DeletionException;
import com.nakao.pointofsale.exception.NotFoundException;
import com.nakao.pointofsale.exception.BusinessLogicException;
import com.nakao.pointofsale.model.Order;
import com.nakao.pointofsale.model.OrderItem;
import com.nakao.pointofsale.model.Product;
import com.nakao.pointofsale.repository.OrderRepository;
import com.nakao.pointofsale.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDAO orderDAO;
    private final ProductRepository productRepository;

    public List<Order> getOrders(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Order> page = orderRepository.findAll(pageable);
        return page.getContent();
    }

    public Order getOrderById(String  id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + id));
    }

    public String createOrder(Order order) {
        order.setId(UUID.randomUUID().toString());
        orderDAO.insert(order);
        return order.getId();
    }

    public void updateOrder(String id, Order order) {
        Order updatedOrder = getOrderById(id);
        BeanUtils.copyProperties(order, updatedOrder);
        updatedOrder.setId(id);
        orderRepository.save(updatedOrder);
    }

    public void deleteOrder(String  id) {
        try {
            validateOrderStatus(getOrderById(id));
            orderRepository.deleteById(id);
        }
        catch (BusinessLogicException e) {
            throw new DeletionException("Unable to delete Order with ID: " + id);
        }
    }

    public void addItem(String id, OrderItem orderItem) {
        Order order = getOrderById(id);
        validateAvailableStock(orderItem.getProductSku());
        validateOrderStatus(order);
        updateOrderWithNewItem(order, orderItem);
    }

    public void removeItem(String orderId, String productSku) {
        Order order = getOrderById(orderId);
        validateOrderStatus(order);
        String orderItemId = orderRepository.getFirstOrderItemIdWithProductSku(orderId, productSku);

        if (orderItemId != null) {
            orderDAO.deleteItem(orderItemId);
            updateOrderPrice(order);
        } else {
            throw new NotFoundException("Order item with product SKU: " + productSku + " not found in order with ID: " + orderId);
        }
    }

    public void processOrder(String id) {
        Order order = getOrderById(id);
        order.setDate(LocalDate.now());
        order.setStatus(OrderStatus.PROCESSED.getValue());
        orderRepository.save(order);
    }

    private void validateAvailableStock(String productSku) {
        Product product = productRepository.findById(productSku)
                .orElseThrow(() -> new NotFoundException("Product not found with SKU: " + productSku));

        if (product.getStock() == 0) {
            throw new BusinessLogicException("Not available stock for product with SKU: " + productSku);
        }
    }

    private void validateOrderStatus(Order order) {
        if (!order.getStatus().equals(OrderStatus.IN_PROGRESS.getValue())) {
            throw new BusinessLogicException("Order with ID: " + order.getId() + " already processed");
        }
    }

    private void updateOrderWithNewItem(Order order, OrderItem orderItem) {
        orderItem.setId(UUID.randomUUID().toString());
        orderDAO.insertItem(orderItem);
        updateOrderPrice(order);
    }

    private void updateOrderPrice(Order order) {
        BigDecimal net = orderRepository.getOrderItemsNetSum(order.getId());
        BigDecimal tax = net.multiply(BigDecimal.valueOf(0.10));

        order.setNet(net);
        order.setTax(tax);
        order.setTotal(net.add(tax));

        orderRepository.save(order);
    }

}
