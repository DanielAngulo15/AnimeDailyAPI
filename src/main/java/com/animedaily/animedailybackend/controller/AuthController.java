package com.animedaily.animedailybackend.controller;

import com.animedaily.animedailybackend.config.JwtUtil;
import com.animedaily.animedailybackend.dto.LoginRequest;
import com.animedaily.animedailybackend.dto.UserRegisterDTO;
import com.animedaily.animedailybackend.model.Usuario;
import com.animedaily.animedailybackend.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody UserRegisterDTO dto) {
        return ResponseEntity.ok(usuarioService.registrarUsuario(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        try {
            // Autenticar credenciales
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContraseña())
            );
            
            // Generar tokens
            Map<String, String> tokens = new HashMap<>();
            tokens.put("token", jwtUtil.generarToken(request.getCorreo()));
            tokens.put("refreshToken", jwtUtil.generarRefreshToken(request.getCorreo()));
            
            return ResponseEntity.ok(tokens);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(
            @RequestHeader("Refresh-Token") String refreshToken) {
        try {
            if (!jwtUtil.validarToken(refreshToken)) {
                return ResponseEntity.status(401).body(Map.of("error", "Refresh token inválido"));
            }
            
            String email = jwtUtil.obtenerUsername(refreshToken);
            Map<String, String> response = new HashMap<>();
            response.put("token", jwtUtil.generarToken(email));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Token inválido"));
        }
    }
}