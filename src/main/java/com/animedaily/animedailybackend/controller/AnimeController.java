package com.animedaily.animedailybackend.controller;

import com.animedaily.animedailybackend.model.Anime;
import com.animedaily.animedailybackend.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/animes")
@CrossOrigin(origins = "*")
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    // Obtener todos los animes (ahora con paginación)
    @GetMapping
    public Page<Anime> getTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort) {
        
        Pageable pageable = createPageable(page, size, sort);
        return animeService.obtenerTodos(pageable);
    }

    // Obtener los animes más populares (sin paginación)
    @GetMapping("/populares")
    public List<Anime> getPopulares() {
        return animeService.obtenerPopulares();
    }

    // Obtener los animes más recientes (sin paginación)
    @GetMapping("/recientes")
    public List<Anime> getRecientes() {
        return animeService.obtenerRecientes();
    }

    // Obtener los animes mejor puntuados (sin paginación)
    @GetMapping("/mejor-puntuados")
    public List<Anime> getMejorPuntuados() {
        return animeService.obtenerMejorPuntuados();
    }

    // Buscar por género (ahora con paginación)
    @GetMapping("/genero/{genero}")
    public Page<Anime> getPorGenero(
            @PathVariable String genero,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort) {
        
        Pageable pageable = createPageable(page, size, sort);
        return animeService.buscarPorGenero(genero, pageable);
    }

    // Buscar por estado (ahora con paginación)
    @GetMapping("/estado/{estado}")
    public Page<Anime> getPorEstado(
            @PathVariable String estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort) {
        
        Pageable pageable = createPageable(page, size, sort);
        return animeService.buscarPorEstado(estado, pageable);
    }

    // Buscar por título (ahora con paginación)
    @GetMapping("/titulo/{titulo}")
    public Page<Anime> getPorTitulo(
            @PathVariable String titulo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort) {
        
        Pageable pageable = createPageable(page, size, sort);
        return animeService.buscarPorTitulo(titulo, pageable);
    }

    // Buscar por temporada de estreno (ahora con paginación)
    @GetMapping("/temporada/{temporada}")
    public Page<Anime> getPorTemporada(
            @PathVariable String temporada,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort) {
        
        Pageable pageable = createPageable(page, size, sort);
        return animeService.buscarPorTemporada(temporada, pageable);
    }

    // Buscar por año de estreno (ahora con paginación)
    @GetMapping("/anio/{anio}")
    public Page<Anime> getPorAnio(
            @PathVariable Integer anio,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort) {
        
        Pageable pageable = createPageable(page, size, sort);
        return animeService.buscarPorAnio(anio, pageable);
    }

    // Obtener los animes favoritos (sin paginación)
    @GetMapping("/favoritos")
    public List<Anime> getFavoritos() {
        return animeService.obtenerFavoritos();
    }

    // Obtener los animes con más miembros (sin paginación)
    @GetMapping("/miembros")
    public List<Anime> getMasMiembros() {
        return animeService.obtenerConMasMiembros();
    }

    // Buscar por puntuación mínima (ahora con paginación)
    @GetMapping("/puntuacion-minima/{puntuacion}")
    public Page<Anime> getPorPuntuacionMinima(
            @PathVariable Double puntuacion,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort) {
        
        Pageable pageable = createPageable(page, size, sort);
        return animeService.buscarPorPuntuacionMinima(puntuacion, pageable);
    }

    // Buscar por tipo (ahora con paginación)
    @GetMapping("/tipo/{tipo}")
    public Page<Anime> getPorTipo(
            @PathVariable String tipo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort) {
        
        Pageable pageable = createPageable(page, size, sort);
        return animeService.buscarPorTipo(tipo, pageable);
    }

    // Buscar por clasificación (ahora con paginación)
    @GetMapping("/clasificacion/{clasificacion}")
    public Page<Anime> getPorClasificacion(
            @PathVariable String clasificacion,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort) {
        
        Pageable pageable = createPageable(page, size, sort);
        return animeService.buscarPorClasificacion(clasificacion, pageable);
    }

    // Buscar por duración (ahora con paginación)
    @GetMapping("/duracion/{duracion}")
    public Page<Anime> getPorDuracion(
            @PathVariable String duracion,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort) {
        
        Pageable pageable = createPageable(page, size, sort);
        return animeService.buscarPorDuracion(duracion, pageable);
    }

    // Obtener por ID (sin cambios)
    @GetMapping("/ID/{id}")
    public ResponseEntity<Anime> getPorID(@PathVariable long id) {
        Optional<Anime> optionalAnime = animeService.obtenerPorID(id);
        return optionalAnime
                .map(anime -> ResponseEntity.ok(anime))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Método auxiliar para crear Pageable
    private Pageable createPageable(int page, int size, String sort) {
        if (sort != null && !sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            String sortField = sortParams[0];
            Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc") 
                    ? Sort.Direction.DESC : Sort.Direction.ASC;
            return PageRequest.of(page, size, Sort.by(direction, sortField));
        }
        return PageRequest.of(page, size);
    }

}