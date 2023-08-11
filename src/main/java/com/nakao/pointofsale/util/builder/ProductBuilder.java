package com.nakao.pointofsale.util.builder;

import com.nakao.pointofsale.model.Product;

import java.math.BigDecimal;

public class ProductBuilder {
    private String sku;
    private String name;
    private String categoryId;
    private Integer stock;
    private Integer minStock;
    private BigDecimal acquisitionCost;
    private BigDecimal sellingPrice;

    public ProductBuilder sku(String sku) {
        this.sku = sku;
        return this;
    }

    public ProductBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder categoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public ProductBuilder stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public ProductBuilder minStock(Integer minStock) {
        this.minStock = minStock;
        return this;
    }

    public ProductBuilder acquisitionCost(BigDecimal acquisitionCost) {
        this.acquisitionCost = acquisitionCost;
        return this;
    }

    public ProductBuilder sellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public Product build() {
        return new Product(name, categoryId, stock, minStock, acquisitionCost, sellingPrice);
    }
}

