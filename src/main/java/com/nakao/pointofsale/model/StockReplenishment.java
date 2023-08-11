package com.nakao.pointofsale.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nakao.pointofsale.annotation.ForeignKeyValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "stock_replenishment")
public class StockReplenishment {
    @Id
    private String id;
    private LocalDate deliveryDate;

    @NotBlank(message = "Product is needed")
    @ForeignKeyValidation(tableName = "product", fieldName = "sku", message = "Non existing product")
    private String productSku;

    private Integer productQuantity;

    @NotNull(message = "Supplier is needed")
    @ForeignKeyValidation(tableName = "supplier", message = "Non existing supplier")
    private Long supplierId;

    @NotBlank(message = "Status cannot be empty")
    @Pattern(regexp = "^(PENDING|DELIVERED)$", message = "Status must be in [PENDING, DELIVERED]")
    private String status;

    public StockReplenishment(LocalDate deliveryDate, String productSku, Integer productQuantity, Long supplierId,
                              String status) {
        this.deliveryDate = deliveryDate;
        this.productSku = productSku;
        this.productQuantity = productQuantity;
        this.supplierId = supplierId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
