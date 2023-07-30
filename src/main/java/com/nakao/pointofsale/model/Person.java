package com.nakao.pos.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Person {

    @NotBlank(message = "First Name cannot be empty")
    @Size(max = 50, message = "The first name is too long")
    protected String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Size(max = 50, message = "The last name name is too long")
    protected String lastName;


    @Pattern(regexp = "[\\d-]+", message = "Phone number should contain only digits and hyphens")
    @Size(max = 20, message = "The phone number is too long")
    protected String phone;

}
