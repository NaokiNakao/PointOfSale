package com.nakao.pointofsale.util.builder;

import com.nakao.pointofsale.model.Supplier;

public class SupplierBuilder {
    private String name;
    private String address;
    private String contact;

    public SupplierBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SupplierBuilder address(String address) {
        this.address = address;
        return this;
    }

    public SupplierBuilder contact(String contact) {
        this.contact = contact;
        return this;
    }

    public Supplier build() {
        return new Supplier(name, address, contact);
    }
}

