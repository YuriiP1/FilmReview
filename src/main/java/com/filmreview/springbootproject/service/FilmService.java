package com.filmreview.springbootproject.service;

import com.filmreview.springbootproject.model.Film;
import com.filmreview.springbootproject.repository.FilmRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    public void save(Film film) {
        if(film != null) {
            filmRepository.save(film);
        }
    }


}
