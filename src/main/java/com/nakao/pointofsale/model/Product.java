package com.nakao.pos.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nakao.pos.validation.ForeignKeyValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
