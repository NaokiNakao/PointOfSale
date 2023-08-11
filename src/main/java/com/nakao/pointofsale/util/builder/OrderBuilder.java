package com.nakao.pointofsale.util.builder;

import com.nakao.pointofsale.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderBuilder {
    private LocalDateTime purchaseDate;
    private BigDecimal net;
    private BigDecimal tax;
    private BigDecimal total;
    private String paymentMethod;
    private String status;
    private String customerId;
    private String employeeId;

    public OrderBuilder purchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public OrderBuilder net(BigDecimal net) {
        this.net = net;
        return this;
    }

    public OrderBuilder tax(BigDecimal tax) {
        this.tax = tax;
        return this;
    }

    public OrderBuilder total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public OrderBuilder paymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderBuilder status(String status) {
        this.status = status;
        return this;
    }

    public OrderBuilder customerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderBuilder employeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public Order build() {
        return new Order(purchaseDate, net, tax, total, paymentMethod, status, customerId, employeeId);
    }
}

