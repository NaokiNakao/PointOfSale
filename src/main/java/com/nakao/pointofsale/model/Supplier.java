package com.nakao.pos.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Naoki Nakao on 7/14/2023
 * @project POS
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private String contact;

}
