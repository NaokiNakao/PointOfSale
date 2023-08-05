package com.nakao.pointofsale.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nakao.pointofsale.annotation.ForeignKeyValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "password_reset_request")
public class PasswordReset {

    @Id
    private String token;

    @NotBlank(message = "Email cannot be empty")
    @Size(max = 255, message = "The email is too long")
    @Email(message = "Not valid email")
    @ForeignKeyValidation(tableName = "employees", fieldName = "email", message = "Non existing email")
    private String email;

    private LocalDateTime expirationDate;

}
