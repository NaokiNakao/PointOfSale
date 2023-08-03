package com.nakao.pointofsale.enumeration;

public enum OrderStatus {

    IN_PROGRESS("IN_PROGRESS"),
    PROCESSED("PROCESSED");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
