package com.animedaily.animedailybackend.mapper;

import com.animedaily.animedailybackend.dto.ListaAnimeDTO;
import com.animedaily.animedailybackend.model.Anime;
import com.animedaily.animedailybackend.model.ListaAnime;
import com.animedaily.animedailybackend.model.Usuario;
import com.animedaily.animedailybackend.repository.AnimeRepository;
import com.animedaily.animedailybackend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListaAnimeMapper {

    private final UsuarioRepository usuarioRepository;
    private final AnimeRepository animeRepository;

    public ListaAnime toEntity(ListaAnimeDTO dto) {
        ListaAnime listaAnime = new ListaAnime();
        listaAnime.setId(dto.getId());
        listaAnime.setNombreLista(dto.getNombreLista());

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        listaAnime.setUsuario(usuario);

        listaAnime.setAnimes(
            dto.getAnimeIds().stream()
                .map(id -> animeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Anime no encontrado: " + id)))
                .collect(Collectors.toList())
        );

        return listaAnime;
    }

    public ListaAnimeDTO toDTO(ListaAnime entity) {
        ListaAnimeDTO dto = new ListaAnimeDTO();
        dto.setId(entity.getId());
        dto.setNombreLista(entity.getNombreLista());
        dto.setUsuarioId(entity.getUsuario().getId());
        dto.setAnimeIds(
            entity.getAnimes().stream()
                .map(Anime::getId)
                .collect(Collectors.toList())
        );

        return dto;
    }
}
