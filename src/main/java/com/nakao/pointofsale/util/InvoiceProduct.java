package com.nakao.pointofsale.util;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceProduct {

    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;

}
