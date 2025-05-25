package com.animedaily.animedailybackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animedaily.animedailybackend.service.AnimeSeederService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AnimeSeederService animeSeederService;

    @GetMapping("/importar-animes")
    public String importar() {
        animeSeederService.importarAnimesDesdeJikan(1146);
        return "Animes importados correctamente desde Jikan.";
    }
}

