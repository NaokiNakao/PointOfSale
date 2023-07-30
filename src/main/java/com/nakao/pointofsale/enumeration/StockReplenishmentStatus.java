package com.nakao.pos.enumeration;

/**
 * @author Naoki Nakao on 7/20/2023
 * @project POS
 */
public enum StockReplenishmentStatus {

    PENDING("PENDING"),
    DELIVERED("DELIVERED");

    private final String value;

    StockReplenishmentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
