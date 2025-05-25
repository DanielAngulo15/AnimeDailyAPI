package com.animedaily.animedailybackend.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class UserRegisterDTO {

    @NotBlank(message = "El nombre no puede estar vacío.")
    private String nombre;

    @Email(message = "Correo electrónico no válido.")
    private String correo;

    @NotBlank(message = "El username no puede estar vacío.")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String contraseña;
}
