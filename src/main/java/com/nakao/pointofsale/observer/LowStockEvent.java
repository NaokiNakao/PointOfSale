package com.nakao.pos.observer;

import com.nakao.pos.model.Product;

/**
 * @author Naoki Nakao on 7/28/2023
 * @project POS
 */
public class LowStockEvent {

    private final Product product;

    public LowStockEvent(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

}
