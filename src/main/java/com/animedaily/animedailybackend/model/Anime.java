package com.animedaily.animedailybackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "animes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mal_id", unique = true)
    private Integer malId;

    private String titulo;

    @Column(name = "titulo_japones")
    private String tituloJapones;

    @Column(name = "titulo_ingles")
    private String tituloIngles;

    // Campos nuevos
    private String formato;

    @Column(name = "temporada_estreno")
    private String temporadaEstreno;

    @Column(name = "anio_estreno")
    private Integer anioEstreno;

    @Column(columnDefinition = "TEXT")
    private String estudios;
    @Column(length = 500)
    private String streaming;

    private String emisora;
    private String malUrl;

    // Campos existentes
    private String tipo;

    private String origen;

    private Integer episodios;

    private String estado;

    @Column(name = "fecha_estreno")
    private LocalDate fechaEstreno;

    @Column(name = "fecha_final")
    private LocalDate fechaFinal;

    private String duracion;

    private Double puntuacion;

    @Column(name = "puntuado_por")
    private Integer puntuadoPor;

    private Integer ranking;

    private Integer popularidad;

    private Integer miembros;

    private Integer favoritos;

    private String clasificacion;

    private String genero;

    private String temas;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "imagen_url", columnDefinition = "TEXT")
    private String imagenUrl;

    @Column(name = "trailer_url", columnDefinition = "TEXT")
    private String trailerUrl;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
