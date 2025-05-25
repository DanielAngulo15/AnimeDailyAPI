package com.animedaily.animedailybackend.dto;

import lombok.Data;
import java.util.List;

@Data
public class ListaAnimeDTO {
    private Long id;
    private String nombreLista;
    private Long usuarioId;
    private List<Long> animeIds;
}
