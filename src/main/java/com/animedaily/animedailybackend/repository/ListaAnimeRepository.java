package com.animedaily.animedailybackend.repository;

import com.animedaily.animedailybackend.model.ListaAnime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ListaAnimeRepository extends JpaRepository<ListaAnime, Long> {
    List<ListaAnime> findByUsuarioId(Long usuarioId);
}
