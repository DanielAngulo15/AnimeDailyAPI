package com.animedaily.animedailybackend.model;
import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
@Entity
@Table(name = "listas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaAnime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreLista;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
        name = "anime_en_lista",
        joinColumns = @JoinColumn(name = "lista_id"),
        inverseJoinColumns = @JoinColumn(name = "anime_id")
    )
    private List<Anime> animes = new ArrayList<>();
    
    private LocalDateTime fechaCreacion = LocalDateTime.now(); 
}
