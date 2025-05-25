package com.animedaily.animedailybackend.service;

import com.animedaily.animedailybackend.client.JikanClient;
import com.animedaily.animedailybackend.model.Anime;
import com.animedaily.animedailybackend.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimeSeederService {
    @Autowired
    private JikanClient jikanClient;

    @Autowired
    private AnimeRepository animeRepository;

    public void importarAnimesDesdeJikan(int paginas) {
        System.out.println("Iniciando importación de animes desde Jikan...");

        for (int i = 1; i <= paginas; i++) {
            List<JikanClient.AnimeJikanDto> animesJikan = obtenerAnimesConRetry(i);
            System.out.println("Página " + i + ": " + animesJikan.size() + " animes obtenidos.");

            for (JikanClient.AnimeJikanDto dto : animesJikan) {
                if (dto != null && dto.malId != null) {
                    if (animeRepository.findByMalId(dto.malId).isEmpty()) {
                        try {
                            // Obtener datos completos del anime (incluyendo streaming)
                            JikanClient.AnimeFullDto animeFull = jikanClient.obtenerAnimeCompleto(dto.malId);
                            
                            if (animeFull == null || animeFull.data == null) {
                                System.err.println("No se pudieron obtener datos completos para anime ID: " + dto.malId);
                                continue;
                            }

                            Anime anime = mapearAnimeDesdeDto(animeFull.data);
                            animeRepository.save(anime);
                            System.out.println("Anime guardado exitosamente: " + anime.getTitulo());
                        } catch (Exception e) {
                            System.err.println("Error al guardar anime ID " + dto.malId + ": " + e.getMessage());
                        }
                    } else {
                        System.out.println("Anime con MalId " + dto.malId + " ya existe.");
                    }
                } else {
                    System.err.println("Datos nulos para el anime: " + dto);
                }
            }
            
            // Esperar entre páginas para evitar rate limiting
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private Anime mapearAnimeDesdeDto(JikanClient.AnimeDetalladoDto dto) {
        Anime anime = new Anime();
        
        // Mapeo de campos básicos
        anime.setMalId(dto.malId);
        anime.setTitulo(dto.title);
        anime.setTituloJapones(dto.titleJapanese);
        anime.setTituloIngles(dto.titleEnglish);
        anime.setTipo(dto.type);
        anime.setOrigen(dto.source);
        anime.setEpisodios(dto.episodes != null ? dto.episodes : 0);
        anime.setEstado(dto.status);
        anime.setFechaEstreno(parseDate(dto.aired != null ? dto.aired.from : null));
        anime.setFechaFinal(parseDate(dto.aired != null ? dto.aired.to : null));
        anime.setDuracion(dto.duration);
        anime.setPuntuacion(dto.score != null ? dto.score : 0.0);
        anime.setPuntuadoPor(dto.scoredBy != null ? dto.scoredBy : 0);
        anime.setRanking(dto.rank != null ? dto.rank : 0);
        anime.setPopularidad(dto.popularity != null ? dto.popularity : 0);
        anime.setMiembros(dto.members != null ? dto.members : 0);
        anime.setFavoritos(dto.favorites != null ? dto.favorites : 0);
        anime.setClasificacion(dto.rating);
        anime.setDescripcion(dto.synopsis);
        anime.setImagenUrl(dto.images != null && dto.images.jpg != null ? dto.images.jpg.imageUrl : null);
        anime.setTrailerUrl(dto.trailer != null ? dto.trailer.url : null);
        anime.setFormato(dto.type);
        anime.setTemporadaEstreno(dto.season);
        anime.setAnioEstreno(dto.year != null ? dto.year : 0);
        
        // Mapeo de estudios
        if (dto.studios != null) {
            String estudios = dto.studios.stream()
                .map(s -> s.name)
                .collect(Collectors.joining(", "));
            anime.setEstudios(estudios);
        } else {
            anime.setEstudios("Desconocidos");
        }
        
        anime.setEmisora(dto.broadcast != null ? dto.broadcast.broadcastString : null);
        anime.setMalUrl(dto.url);

        // Mapeo de géneros
        if (dto.genres != null) {
            String generos = dto.genres.stream()
                .map(g -> g.name)
                .collect(Collectors.joining(", "));
            anime.setGenero(generos);
        }

        // Mapeo de temas
        if (dto.themes != null) {
            String temas = dto.themes.stream()
                .map(t -> t.name)
                .collect(Collectors.joining(", "));
            anime.setTemas(temas);
        }

        // Mapeo de servicios de streaming (nuevo)
        if (dto.streaming != null) {
            String streaming = dto.streaming.stream()
                .map(s -> s.nombre)
                .collect(Collectors.joining(", "));
            anime.setStreaming(streaming);
        }

        return anime;
    }

    private List<JikanClient.AnimeJikanDto> obtenerAnimesConRetry(int pagina) {
        int intentos = 0;
        int maxIntentos = 5;
        long tiempoEspera = 2000;

        while (intentos < maxIntentos) {
            try {
                return jikanClient.obtenerTopAnimes(pagina);
            } catch (HttpClientErrorException.TooManyRequests e) {
                System.out.println("Error 429: Too many requests. Esperando antes de reintentar...");
                try {
                    Thread.sleep(tiempoEspera);
                    tiempoEspera *= 2;
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                intentos++;
            }
        }
        throw new RuntimeException("Se alcanzó el límite de reintentos. No se pudo obtener los animes.");
    }

    private LocalDate parseDate(String dateStr) {
        try {
            return dateStr != null ? LocalDate.parse(dateStr.substring(0, 10)) : null;
        } catch (Exception e) {
            return null;
        }
    }
}