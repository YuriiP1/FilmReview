package com.filmreview.springbootproject.service;

import com.filmreview.springbootproject.model.Film;
import com.filmreview.springbootproject.repository.FilmRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> findAll(int pageNumber, int rowPerPage) {
        List<Film> films = new ArrayList<>();
        Pageable sortedByIdAsc = PageRequest.of(pageNumber-1, rowPerPage,
                Sort.by("id").ascending());
        filmRepository.findAll(sortedByIdAsc).forEach(films::add);
        return films;
    }

    public void save(Film film) {
        if(film != null) {
            filmRepository.save(film);
        }
    }

    public long count() {
        return filmRepository.count();
    }


}
