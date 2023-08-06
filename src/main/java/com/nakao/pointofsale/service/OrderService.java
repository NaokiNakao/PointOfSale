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
import java.time.LocalDateTime;
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

    /**
     * Adds an order item to the specified order, updates its total price, and saves the changes.
     *
     * @param id The ID of the order to which the item will be added.
     * @param orderItem The order item to be added.
     * @throws BusinessLogicException If the order's status is not "IN_PROGRESS" or if there is no available stock
     *                               for the product associated with the order item.
     * @throws NotFoundException If the product associated with the order item is not found.
     */
    public void addItem(String id, OrderItem orderItem) {
        Order order = getOrderById(id);
        validateAvailableStock(orderItem.getProductSku());
        validateOrderStatus(order);
        updateOrderWithNewItem(order, orderItem);
    }

    /**
     * Removes an order item with the specified product SKU from the given order, updates the order's total price,
     * and saves the changes.
     *
     * @param orderId The ID of the order from which the item will be removed.
     * @param productSku The SKU of the product associated with the item to be removed.
     * @throws BusinessLogicException If the order's status is not "IN_PROGRESS".
     * @throws NotFoundException If the order item with the specified product SKU is not found in the order.
     */
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

    /**
     * Processes an order by updating its date, status to "PROCESSED", and decreasing stock units
     * for the associated products.
     * Saves the changes to the order and updates the product stock accordingly.
     *
     * @param id The ID of the order to be processed.
     * @throws BusinessLogicException If the order is already processed or if there is insufficient
     *                              stock for any of the associated products.
     * @throws NotFoundException If the order with the specified ID is not found.
     */
    public void processOrder(String id) {
        Order order = getOrderById(id);
        order.setPurchaseDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PROCESSED.getValue());
        decreaseStockUnits(id);
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

    private void decreaseStockUnits(String id) {
        List<OrderItem> orderItems = orderRepository.getOrderItems(id);

        for (OrderItem orderItem : orderItems) {
            productRepository.decreaseStock(orderItem.getProductSku());
        }
    }

}
