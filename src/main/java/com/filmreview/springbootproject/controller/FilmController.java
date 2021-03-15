package com.filmreview.springbootproject.controller;

import com.filmreview.springbootproject.exception.BadResourceException;
import com.filmreview.springbootproject.exception.ResourceAlreadyExistsException;
import com.filmreview.springbootproject.exception.ResourceNotFoundException;
import com.filmreview.springbootproject.model.Film;
import com.filmreview.springbootproject.service.FilmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class FilmController {
    private final int ROW_PER_PAGE = 3;
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/home")
    public String findAll(Model model,
                          @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<Film> films = filmService.findAll(pageNumber, ROW_PER_PAGE);
        long count = filmService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("films", films);
        model.addAttribute("hasPrev",hasPrev);
        model.addAttribute("prev",pageNumber-1);
        model.addAttribute("hasNext",hasNext);
        model.addAttribute("next",pageNumber+1);
        return "home";
    }

    @GetMapping("/home/add-film")
    public String showAddFilm(Model model) {
        Film film = new Film();
        model.addAttribute("add",true);
        model.addAttribute("film",film);
        return "film_edit";
    }

    @PostMapping("/home/add-film")
    public String addFilm(Model model,
                          @ModelAttribute("film") @Valid Film film,
                          BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "film_edit";
        }
        try {
            filmService.save(film);
            return "redirect:/home";
        } catch (ResourceAlreadyExistsException | BadResourceException e) {
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage",errorMessage);
            model.addAttribute("add",true);
            return "film_edit";
        }
    }

    @GetMapping("/home/{filmId}/edit")
    public String showEditFilm(Model model,
                               @PathVariable long filmId) {
        Film film = null;

        try{
            film = filmService.findById(filmId);
        } catch (ResourceNotFoundException e) {
            model.addAttribute("errorMessage","Film not found");
        }
        model.addAttribute("add",false);
        model.addAttribute("film",film);
        return "film_edit";
    }

    @PostMapping("/home/{filmId}/edit")
    public String editFilm(Model model,
                           @PathVariable long filmId,
                           @ModelAttribute("film") Film film) {
        try{
            film.setId(filmId);
            filmService.update(film);
            return "redirect:/films/" + String.valueOf(film.getId());

        } catch (BadResourceException | ResourceNotFoundException e) {
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage",errorMessage);
            model.addAttribute("add",false);

            return "film_edit";
        }
    }

    @GetMapping("/home/films/{filmId}")
    public String getFilmById(Model model,
                              @PathVariable long filmId) {
        Film film = null;
        try{
            film = filmService.findById(filmId);
        } catch (ResourceNotFoundException e) {
            model.addAttribute("errorMessage", "Film not found");
        }
        model.addAttribute("film",film);
        return "film";
    }
}
