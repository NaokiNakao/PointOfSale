package com.nakao.pointofsale.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nakao.pointofsale.validation.ForeignKeyValidation;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
