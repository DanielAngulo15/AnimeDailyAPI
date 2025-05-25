package com.animedaily.animedailybackend.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class UserLoginDTO {

    @NotBlank(message = "El username o correo no puede estar vacío.")
    private String usernameOrEmail;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    private String contraseña;
}
