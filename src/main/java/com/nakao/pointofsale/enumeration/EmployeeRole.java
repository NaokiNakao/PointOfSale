package com.nakao.pointofsale.enumeration;

public enum EmployeeRole {

    CASHIER("CASHIER"),
    MANAGER("MANAGER"),
    EMPLOYEE("EMPLOYEE");

    private final String value;

    EmployeeRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

