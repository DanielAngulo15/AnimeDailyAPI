package com.animedaily.animedailybackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnimeSearchCriteria {
    private List<String> genero;
    private Integer anio;
    private String temporada;
    private String formato;
    private String tipo;
    private String estado;
    private Double puntuacionMinima;
    private String clasificacion;
}