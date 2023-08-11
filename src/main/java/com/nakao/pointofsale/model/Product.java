package com.nakao.pointofsale.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nakao.pointofsale.annotation.ForeignKeyValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "product")
public class Product {
    @Id
    private String sku;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 50, message = "The name is too long")
    private String name;

    @NotBlank(message = "Category is needed")
    @ForeignKeyValidation(tableName = "category", message = "Non existing Category")
    private String categoryId;

    private Integer stock;

    private Integer minStock;

    private BigDecimal acquisitionCost;

    private BigDecimal sellingPrice;

    public static final String SKU_PATTERN = "PROD-####-***";

    public Product(String name, String categoryId, Integer stock, Integer minStock, BigDecimal acquisitionCost,
                   BigDecimal sellingPrice) {
        this.name = name;
        this.categoryId = categoryId;
        this.stock = stock;
        this.minStock = minStock;
        this.acquisitionCost = acquisitionCost;
        this.sellingPrice = sellingPrice;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getMinStock() {
        return minStock;
    }

    public void setMinStock(Integer minStock) {
        this.minStock = minStock;
    }

    public BigDecimal getAcquisitionCost() {
        return acquisitionCost;
    }

    public void setAcquisitionCost(BigDecimal acquisitionCost) {
        this.acquisitionCost = acquisitionCost;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
