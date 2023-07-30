package com.nakao.pos.enumeration;

/**
 * @author Naoki Nakao on 7/20/2023
 * @project POS
 */
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
