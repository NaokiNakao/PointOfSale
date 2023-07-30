package com.nakao.pointofsale.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "customer")
public class Customer extends Person {

    @Id
    private String id;

    @Size(max = 100, message = "The address is too long")
    private String address;

    private LocalDate birthday;

    public static final String ID_PATTERN = "CUST-####-***";

    @Builder
    public Customer(String firstName, String lastName, String phone, String id, String address, LocalDate birthday) {
        super(firstName, lastName, phone);
        this.id = id;
        this.address = address;
        this.birthday = birthday;
    }

}
