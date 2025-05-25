package com.animedaily.animedailybackend.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.animedaily.animedailybackend.dto.AnimeSearchCriteria;
import com.animedaily.animedailybackend.model.Anime;
import com.animedaily.animedailybackend.repository.AnimeRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnimeSearchService {

    @Autowired
    private AnimeRepository animeRepository;

    public Page<Anime> searchAnimes(AnimeSearchCriteria criteria, Pageable pageable) {
        // Primero obtenemos todos los animes paginados
        Page<Anime> page = animeRepository.findAll(pageable);
        List<Anime> filtered = new ArrayList<>(page.getContent());

        // Aplicamos filtros uno por uno
        if (criteria.getGenero() != null && !criteria.getGenero().isEmpty()) {
            filtered = filterByGeneros(filtered, criteria.getGenero());
        }
        
        if (criteria.getAnio() != null) {
            filtered = filtered.stream()
                .filter(a -> a.getAnioEstreno() != null && a.getAnioEstreno().equals(criteria.getAnio()))
                .collect(Collectors.toList());
        }
        
        if (criteria.getTemporada() != null) {
            filtered = filtered.stream()
                .filter(a -> a.getTemporadaEstreno() != null && 
                             a.getTemporadaEstreno().equalsIgnoreCase(criteria.getTemporada()))
                .collect(Collectors.toList());
        }
        
        if (criteria.getFormato() != null) {
            filtered = filtered.stream()
                .filter(a -> a.getTipo() != null && 
                             a.getTipo().equalsIgnoreCase(criteria.getFormato()))
                .collect(Collectors.toList());
        }
        
        // Convertimos la lista filtrada de vuelta a Page
        return new PageImpl<>(filtered, pageable, filtered.size());
    }

private List<Anime> filterByGeneros(List<Anime> animes, List<String> generosBuscados) {
    return animes.stream()
        .filter(anime -> {
            if (anime.getGenero() == null) return false;
            
            // Normaliza los géneros del anime (elimina espacios y divide por comas)
            List<String> generosAnime = Arrays.stream(anime.getGenero().split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
            
            // Verifica que todos los géneros buscados estén en la lista del anime
            return generosBuscados.stream()
                .map(String::toLowerCase)
                .allMatch(generoBuscado -> generosAnime.contains(generoBuscado));
        })
        .collect(Collectors.toList());
}
}