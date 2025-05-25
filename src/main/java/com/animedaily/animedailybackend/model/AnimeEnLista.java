package com.animedaily.animedailybackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "anime_en_lista")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimeEnLista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lista_id", nullable = false)
    private ListaAnime lista;

    @ManyToOne
    @JoinColumn(name = "anime_id", nullable = false)
    private Anime anime;

    @Column(name = "fecha_agregado")
    private LocalDate fechaAgregado;

    @Column(name = "estado_anime", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoAnime estadoAnime;

}
