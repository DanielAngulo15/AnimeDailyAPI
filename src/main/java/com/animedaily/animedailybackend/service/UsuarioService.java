package com.animedaily.animedailybackend.service;

import org.springframework.security.crypto.password.PasswordEncoder; 
import com.animedaily.animedailybackend.dto.UserRegisterDTO;
import com.animedaily.animedailybackend.model.Usuario;
import com.animedaily.animedailybackend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; 

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(UserRegisterDTO userRegisterDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(userRegisterDTO.getNombre());
        usuario.setCorreo(userRegisterDTO.getCorreo());
        usuario.setUsername(userRegisterDTO.getUsername());
        usuario.setContraseña(passwordEncoder.encode(userRegisterDTO.getContraseña()));

        return usuarioRepository.save(usuario);
    }
}