package com.beytcho.Beytcho.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class LoginRequest {

    @NotBlank(message = "required")
    private String email;
    @NotBlank(message = "required")
    private String password;


}
