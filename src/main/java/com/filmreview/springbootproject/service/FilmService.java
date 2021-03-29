package com.filmreview.springbootproject.service;

import com.filmreview.springbootproject.exception.BadResourceException;
import com.filmreview.springbootproject.exception.ResourceAlreadyExistsException;
import com.filmreview.springbootproject.exception.ResourceNotFoundException;
import com.filmreview.springbootproject.model.Film;
import com.filmreview.springbootproject.repository.FilmRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmService {
    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public Film findById(Long id) throws ResourceNotFoundException {
        Film film = filmRepository.findById(id).orElse(null);
        if(film == null) {
            throw new ResourceNotFoundException("Cannot find Film with id: " + id);
        } else {
            return film;
        }
    }
    public List<Film> findAll(int pageNumber, int rowPerPage) {
        List<Film> films = new ArrayList<>();
        Pageable sortedByIdAsc = PageRequest.of(pageNumber-1, rowPerPage,
                Sort.by("id").ascending());
        filmRepository.findAll(sortedByIdAsc).forEach(films::add);
        return films;
    }

    public Film save(Film film) throws ResourceAlreadyExistsException, BadResourceException {
        if(!StringUtils.isEmpty(film.getTitle())) {
            if(film.getId() != null && existsById(film.getId())) {
                throw new ResourceAlreadyExistsException("Film with id: " + film.getId() + " already exists!");
            }
            return filmRepository.save(film);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save contact");

            exc.addErrorMessage("Film is null or empty");

            throw exc;
        }
    }

    public void update(Film film) throws ResourceNotFoundException, BadResourceException {
        if(!StringUtils.isEmpty(film.getTitle())) {
            if(!existsById(film.getId())) {
                throw new ResourceNotFoundException("Cannot find Film with id: " + film.getId());
            }
            filmRepository.save(film);
        } else {
            BadResourceException e = new BadResourceException("Failed to save film.");
            e.addErrorMessage("Film is null or empty");
            throw e;

        }
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if(!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find Film with id: " + id);
        } else {
            filmRepository.deleteById(id);
        }
    }

    private boolean existsById(Long id) {
        return filmRepository.existsById(id);
    }

    public long count() {
        return filmRepository.count();
    }


}
