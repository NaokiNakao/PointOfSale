package com.nakao.pointofsale.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nakao.pointofsale.annotation.ForeignKeyValidation;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "order_item")
public class OrderItem {
    @Id
    private String id;

    @NotBlank(message = "Product is needed")
    @ForeignKeyValidation(tableName = "product", fieldName = "sku", message = "Non existing product")
    private String productSku;

    @NotBlank(message = "Order is needed")
    @ForeignKeyValidation(tableName = "orders", message = "Non existing order")
    private String orderId;

    public OrderItem(String productSku, String orderId) {
        this.productSku = productSku;
        this.orderId = orderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
