package com.nakao.pointofsale.util.builder;

import com.nakao.pointofsale.model.StockReplenishment;

import java.time.LocalDate;

public class StockReplenishmentBuilder {
    private LocalDate deliveryDate;
    private String productSku;
    private Integer productQuantity;
    private Long supplierId;
    private String status;

    public StockReplenishmentBuilder deliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public StockReplenishmentBuilder productSku(String productSku) {
        this.productSku = productSku;
        return this;
    }

    public StockReplenishmentBuilder productQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
        return this;
    }

    public StockReplenishmentBuilder supplierId(Long supplierId) {
        this.supplierId = supplierId;
        return this;
    }

    public StockReplenishmentBuilder status(String status) {
        this.status = status;
        return this;
    }

    public StockReplenishment build() {
        return new StockReplenishment(deliveryDate, productSku, productQuantity, supplierId, status);
    }
}

