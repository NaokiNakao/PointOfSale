package com.nakao.pointofsale.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nakao.pointofsale.annotation.ForeignKeyValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "orders")
public class Order {
    @Id
    private String id;

    private LocalDateTime purchaseDate;

    private BigDecimal net;

    private BigDecimal tax;

    private BigDecimal total;

    @Pattern(regexp = "^(CASH|CREDIT_CARD|PAYPAL)$", message = "Status must be in [CASH, CREDIT_CARD, PAYPAL]")
    private String paymentMethod;

    @Pattern(regexp = "^(IN_PROGRESS|PROCESSED)$", message = "Status must be in [IN_PROGRESS, PROCESSED]")
    private String status;

    @ForeignKeyValidation(tableName = "customer", message = "Non existing Customer")
    private String customerId;

    @NotBlank(message = "Employee is needed")
    @ForeignKeyValidation(tableName = "employee", message = "Non existing Employee")
    private String employeeId;

    public Order(LocalDateTime purchaseDate, BigDecimal net, BigDecimal tax, BigDecimal total, String paymentMethod,
                 String status, String customerId, String employeeId) {
        this.purchaseDate = purchaseDate;
        this.net = net;
        this.tax = tax;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.customerId = customerId;
        this.employeeId = employeeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getNet() {
        return net;
    }

    public void setNet(BigDecimal net) {
        this.net = net;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
