package com.nakao.pos.enumeration;

/**
 * @author Naoki Nakao on 7/28/2023
 * @project POS
 */
public enum EmployeeRole {

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

