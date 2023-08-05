package com.nakao.pointofsale.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PasswordConfirmation {

    private String password;
    private String confirmPassword;

}
