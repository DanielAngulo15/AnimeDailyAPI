package com.animedaily.animedailybackend.controller;

import com.animedaily.animedailybackend.dto.ListaAnimeDTO;
import com.animedaily.animedailybackend.service.ListaAnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listas")
@RequiredArgsConstructor
public class ListaAnimeController {

    private final ListaAnimeService listaAnimeService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ListaAnimeDTO>> obtenerListasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(listaAnimeService.obtenerListasPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<ListaAnimeDTO> crearLista(@RequestBody ListaAnimeDTO listaAnimeDTO) {
        return ResponseEntity.ok(listaAnimeService.crearLista(listaAnimeDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaAnimeDTO> obtenerListaPorId(@PathVariable Long id) {
        return listaAnimeService.obtenerListaPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaAnimeDTO> actualizarLista(@PathVariable Long id, @RequestBody ListaAnimeDTO listaActualizadaDTO) {
        return ResponseEntity.ok(listaAnimeService.actualizarLista(id, listaActualizadaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLista(@PathVariable Long id) {
        listaAnimeService.eliminarLista(id);
        return ResponseEntity.noContent().build();
    }
}
