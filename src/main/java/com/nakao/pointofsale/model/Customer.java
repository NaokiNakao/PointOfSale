package com.nakao.pointofsale.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "customer")
public class Customer {
    @Id
    private String id;

    @NotBlank(message = "First Name cannot be empty")
    @Size(max = 50, message = "The first name is too long")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Size(max = 50, message = "The last name name is too long")
    private String lastName;

    @Pattern(regexp = "[\\d-]+", message = "Phone number should contain only digits and hyphens")
    @Size(max = 20, message = "The phone number is too long")
    private String phone;

    @Size(max = 100, message = "The address is too long")
    private String address;

    private LocalDate birthday;

    public static final String ID_PATTERN = "CUST-####-***";

    public Customer(String firstName, String lastName, String phone, String address, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
