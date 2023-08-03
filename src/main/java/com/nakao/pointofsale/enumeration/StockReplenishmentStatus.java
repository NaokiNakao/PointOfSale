package com.nakao.pointofsale.enumeration;

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
