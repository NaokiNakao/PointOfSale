package com.nakao.pointofsale.observer;

import com.nakao.pointofsale.model.Product;

public class LowStockEvent {

    private final Product product;

    public LowStockEvent(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

}
