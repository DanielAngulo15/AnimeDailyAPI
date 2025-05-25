package com.animedaily.animedailybackend.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class JikanClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://api.jikan.moe/v4";

    // Obtener el top de animes (paginado)
    public List<AnimeJikanDto> obtenerTopAnimes(int page) {
        String url = BASE_URL + "/top/anime?page=" + page;
        JikanResponse response = restTemplate.getForObject(url, JikanResponse.class);
        return response != null ? response.data : List.of();
    }

    // Obtener detalles completos de un anime (incluyendo streaming)
    public AnimeFullDto obtenerAnimeCompleto(int malId) {
        String url = BASE_URL + "/anime/" + malId + "/full";
        try {
            return restTemplate.getForObject(url, AnimeFullDto.class);
        } catch (Exception e) {
            System.err.println("Error al obtener anime completo ID " + malId + ": " + e.getMessage());
            return null;
        }
    }

    // ================= DTOs (Data Transfer Objects) =================

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JikanResponse {
        public List<AnimeJikanDto> data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AnimeFullDto {
        @JsonProperty("data")
        public AnimeDetalladoDto data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AnimeDetalladoDto extends AnimeJikanDto {
        @JsonProperty("streaming")
        public List<StreamingDto> streaming;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StreamingDto {
        @JsonProperty("name")
        public String nombre;
        
        @JsonProperty("url")
        public String url;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AnimeJikanDto {
        // Identificación
        @JsonProperty("mal_id")
        public Integer malId;
        
        // Títulos
        public String title;
        
        @JsonProperty("title_japanese")
        public String titleJapanese;
        
        @JsonProperty("title_english")
        public String titleEnglish;
        
        // Detalles principales
        public String type;
        public String source;
        public Integer episodes;
        public String status;
        public String duration;
        public Double score;
        
        @JsonProperty("scored_by")
        public Integer scoredBy;
        
        // Rankings
        public Integer rank;
        public Integer popularity;
        public Integer members;
        public Integer favorites;
        public String rating;
        
        // Descripción
        public String synopsis;
        
        // Multimedia
        public Images images;
        public Trailer trailer;
        
        // Fechas de emisión
        @JsonProperty("aired")
        public AiredDates aired;
        
        // Categorías
        public List<GenreDto> genres;
        public List<ThemeDto> themes;
        
        // Producción
        public List<StudioDto> studios;
        
        @JsonProperty("season")
        public String season;
        
        @JsonProperty("year")
        public Integer year;
        
        // Emisión
        @JsonProperty("broadcast")
        public Broadcast broadcast;
        
        // Enlaces
        @JsonProperty("url")
        public String url;

        // ============ Clases anidadas para estructuras complejas ============
        
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Images {
            @JsonProperty("jpg")
            public Jpg jpg;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Jpg {
            @JsonProperty("image_url")
            public String imageUrl;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Trailer {
            @JsonProperty("url")
            public String url;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class AiredDates {
            @JsonProperty("from")
            public String from;
            
            @JsonProperty("to")
            public String to;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Broadcast {
            @JsonProperty("string")
            public String broadcastString;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class GenreDto {
            public String name;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ThemeDto {
            public String name;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class StudioDto {
            public String name;
        }
    }
}