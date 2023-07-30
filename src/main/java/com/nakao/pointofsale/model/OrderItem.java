package com.nakao.pos.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nakao.pos.validation.ForeignKeyValidation;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

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
