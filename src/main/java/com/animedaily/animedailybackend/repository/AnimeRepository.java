package com.animedaily.animedailybackend.repository;

import com.animedaily.animedailybackend.model.Anime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long>{
    // Métodos básicos
    Optional<Anime> findById(Long id);
    Optional<Anime> findByMalId(Integer malId);
    
    // Métodos de top (sin paginación)
    List<Anime> findTop10ByOrderByPopularidadDesc();
    List<Anime> findTop10ByOrderByFechaEstrenoDesc();
    List<Anime> findTop10ByOrderByPuntuacionDesc();
    List<Anime> findTop5ByOrderByFavoritosDesc();
    List<Anime> findTop5ByOrderByMiembrosDesc();
    
    // Métodos con paginación
    Page<Anime> findAll(Pageable pageable);
    Page<Anime> findByGeneroContainingIgnoreCase(String genero, Pageable pageable);
    Page<Anime> findByEstado(String estado, Pageable pageable);
    Page<Anime> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
    Page<Anime> findByTemporadaEstrenoIgnoreCase(String temporada, Pageable pageable);
    Page<Anime> findByAnioEstreno(Integer anio, Pageable pageable);
    Page<Anime> findByPuntuacionGreaterThanEqual(Double puntuacion, Pageable pageable);
    Page<Anime> findByTipoIgnoreCase(String tipo, Pageable pageable);
    Page<Anime> findByClasificacionIgnoreCase(String clasificacion, Pageable pageable);
    Page<Anime> findByDuracionContainingIgnoreCase(String duracion, Pageable pageable);
    
    // Métodos antiguos (opcional, para compatibilidad)
    @Deprecated
    List<Anime> findByGeneroContainingIgnoreCase(String genero);
    @Deprecated
    List<Anime> findByEstado(String estado);
    @Deprecated
    List<Anime> findByTituloContainingIgnoreCase(String titulo);
    @Deprecated
    List<Anime> findByTemporadaEstrenoIgnoreCase(String temporada);
    @Deprecated
    List<Anime> findByAnioEstreno(Integer anio);
    @Deprecated
    List<Anime> findByPuntuacionGreaterThanEqual(Double puntuacion);
    @Deprecated
    List<Anime> findByTipoIgnoreCase(String tipo);
    @Deprecated
    List<Anime> findByClasificacionIgnoreCase(String clasificacion);
    @Deprecated
    List<Anime> findByDuracionContainingIgnoreCase(String duracion);
}