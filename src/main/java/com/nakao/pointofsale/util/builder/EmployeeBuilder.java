package com.nakao.pointofsale.util.builder;

import com.nakao.pointofsale.model.Employee;

public class EmployeeBuilder {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private String role;

    public EmployeeBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public EmployeeBuilder email(String email) {
        this.email = email;
        return this;
    }

    public EmployeeBuilder password(String password) {
        this.password = password;
        return this;
    }

    public EmployeeBuilder role(String role) {
        this.role = role;
        return this;
    }

    public Employee build() {
        return new Employee(firstName, lastName, phone, email, password, role);
    }
}

