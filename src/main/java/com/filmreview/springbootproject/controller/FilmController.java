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
        model.addAttribute("film",film);
        return "add_film";
    }

    @PostMapping("/home/add-film")
    public String addFilm(Model model,
                          @ModelAttribute("film") @Valid Film film,
                          BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "add_film";
        }
        try {
            filmService.save(film);
            return "redirect:/home";
        } catch (ResourceAlreadyExistsException | BadResourceException e) {
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage",errorMessage);
            return "add_film";
        }
    }

    @GetMapping("/home/edit/{filmId}")
    public String showUpdateForm(Model model,
                               @PathVariable long filmId) {

        Film film = null;

        try{
            film = filmService.findById(filmId);
        } catch (ResourceNotFoundException e) {
            model.addAttribute("errorMessage","Film not found");
        }
        model.addAttribute("film",film);
        return "film_edit";
    }

    @PostMapping("/home/update/{filmId}")
    public String updateFilm(Model model,
                           @PathVariable long filmId,
                           @ModelAttribute("film") @Valid Film film) {
        try{
            film.setId(filmId);
            filmService.update(film);
            return "redirect:/home/films/" + String.valueOf(film.getId());

        } catch (BadResourceException | ResourceNotFoundException e) {
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage",errorMessage);

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
