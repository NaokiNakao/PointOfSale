package com.nakao.pos.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nakao.pos.validation.ForeignKeyValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

/**
 * @author Naoki Nakao on 7/14/2023
 * @project POS
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
