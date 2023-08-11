package com.nakao.pointofsale.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "supplier")
public class Supplier {
    @Id
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 50, message = "The name is too long")
    private String name;

    @NotBlank(message = "Address name cannot be empty")
    @Size(max = 50, message = "The address is too long")
    private String address;

    @NotBlank(message = "Contact name cannot be empty")
    @Size(max = 255, message = "The contact is too long")
    @Email(message = "Not valid contact")
    private String email;

    public Supplier(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
