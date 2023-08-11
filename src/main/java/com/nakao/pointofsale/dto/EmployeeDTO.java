package com.nakao.pointofsale.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EmployeeDTO {
    private String id;

    @NotBlank(message = "First Name cannot be empty")
    @Size(max = 50, message = "The first name is too long")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Size(max = 50, message = "The last name name is too long")
    private String lastName;

    @NotBlank(message = "Email cannot be empty")
    @Size(max = 255, message = "The email is too long")
    @Email(message = "Not valid email")
    private String email;

    @Pattern(regexp = "[\\d-]+", message = "Phone number should contain only digits and hyphens")
    @Size(max = 20, message = "The phone number is too long")
    private String phone;

    @NotBlank(message = "Role cannot be empty")
    @Size(max = 20, message = "The role is too long")
    @Pattern(regexp = "^(CASHIER|MANAGER|ADMIN)$", message = "Status must be in [CASHIER, MANAGER, ADMIN]")
    private String role;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String id, String firstName, String lastName, String email, String phone, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
