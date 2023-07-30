package com.nakao.pos.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nakao.pos.validation.ForeignKeyValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "orders")
public class Order {

    @Id
    private String id;
    private LocalDate date;
    private BigDecimal net;
    private BigDecimal tax;
    private BigDecimal total;

    @Pattern(regexp = "^(CASH|CREDIT_CARD)$", message = "Status must be in [CASH, CREDIT_CARD]")
    private String paymentMethod;

    @Pattern(regexp = "^(IN_PROGRESS|PROCESSED)$", message = "Status must be in [IN_PROGRESS, PROCESSED]")
    private String status;

    @ForeignKeyValidation(tableName = "customer", message = "Non existing Customer")
    private String customerId;

    @NotBlank(message = "Employee is needed")
    @ForeignKeyValidation(tableName = "employee", message = "Non existing Employee")
    private String employeeId;

}
