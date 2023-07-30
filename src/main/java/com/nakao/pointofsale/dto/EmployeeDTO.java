package com.nakao.pos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * @author Naoki Nakao on 7/23/2023
 * @project POS
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

}
