package com.nakao.pos.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

/**
 * @author Naoki Nakao on 7/15/2023
 * @project POS
 */

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
