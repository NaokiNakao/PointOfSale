package com.nakao.pos.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Naoki Nakao on 7/13/2023
 * @project POS
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Table(name = "category")
public class Category {

    @Id
    private String id;

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 50, message = "The name is too long")
    private String name;

    public static final String ID_PATTERN = "CAT######";

}
