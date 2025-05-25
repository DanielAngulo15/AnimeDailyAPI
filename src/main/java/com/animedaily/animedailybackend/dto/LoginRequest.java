package com.animedaily.animedailybackend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String correo;
    private String contraseña;
}
