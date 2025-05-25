package com.animedaily.animedailybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimeDTO {
    private Long id;
    private String titulo;
    private BigDecimal puntuacion;
}
