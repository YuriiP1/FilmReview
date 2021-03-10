package com.filmreview.springbootproject.service;

import com.filmreview.springbootproject.repository.FilmRepository;
import org.springframework.data.domain.Page;

public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }
}
