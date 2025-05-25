package com.animedaily.animedailybackend.controller;

import com.animedaily.animedailybackend.dto.AnimeSearchCriteria;
import com.animedaily.animedailybackend.model.Anime;
import com.animedaily.animedailybackend.service.AnimeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/anime/search")
public class AnimeSearchController {

    @Autowired
    private AnimeSearchService animeSearchService;

    @GetMapping
    public Page<Anime> searchAnimes(AnimeSearchCriteria criteria, Pageable pageable) {
        return animeSearchService.searchAnimes(criteria, pageable);
    }
}