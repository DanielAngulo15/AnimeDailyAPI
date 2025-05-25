package com.animedaily.animedailybackend.service;

import com.animedaily.animedailybackend.model.Anime;
import com.animedaily.animedailybackend.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    // Métodos con paginación
    public Page<Anime> obtenerTodos(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public Page<Anime> buscarPorGenero(String genero, Pageable pageable) {
        return animeRepository.findByGeneroContainingIgnoreCase(genero, pageable);
    }

    public Page<Anime> buscarPorEstado(String estado, Pageable pageable) {
        return animeRepository.findByEstado(estado, pageable);
    }

    public Page<Anime> buscarPorTitulo(String titulo, Pageable pageable) {
        return animeRepository.findByTituloContainingIgnoreCase(titulo, pageable);
    }

    public Page<Anime> buscarPorTemporada(String temporada, Pageable pageable) {
        return animeRepository.findByTemporadaEstrenoIgnoreCase(temporada, pageable);
    }

    public Page<Anime> buscarPorAnio(Integer anio, Pageable pageable) {
        return animeRepository.findByAnioEstreno(anio, pageable);
    }

    public Page<Anime> buscarPorPuntuacionMinima(Double puntuacion, Pageable pageable) {
        return animeRepository.findByPuntuacionGreaterThanEqual(puntuacion, pageable);
    }

    public Page<Anime> buscarPorTipo(String tipo, Pageable pageable) {
        return animeRepository.findByTipoIgnoreCase(tipo, pageable);
    }

    public Page<Anime> buscarPorClasificacion(String clasificacion, Pageable pageable) {
        return animeRepository.findByClasificacionIgnoreCase(clasificacion, pageable);
    }

    public Page<Anime> buscarPorDuracion(String duracion, Pageable pageable) {
        return animeRepository.findByDuracionContainingIgnoreCase(duracion, pageable);
    }

    // Métodos sin paginación (para los tops)
    public List<Anime> obtenerPopulares() {
        return animeRepository.findTop10ByOrderByPopularidadDesc();
    }

    public List<Anime> obtenerRecientes() {
        return animeRepository.findTop10ByOrderByFechaEstrenoDesc();
    }

    public List<Anime> obtenerMejorPuntuados() {
        return animeRepository.findTop10ByOrderByPuntuacionDesc();
    }

    public List<Anime> obtenerFavoritos() {
        return animeRepository.findTop5ByOrderByFavoritosDesc();
    }

    public List<Anime> obtenerConMasMiembros() {
        return animeRepository.findTop5ByOrderByMiembrosDesc();
    }

    // Método para obtener por ID
    public Optional<Anime> obtenerPorID(long id) {
        return animeRepository.findById(id);
    }
    


}