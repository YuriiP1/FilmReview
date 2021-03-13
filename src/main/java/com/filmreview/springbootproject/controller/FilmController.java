package com.filmreview.springbootproject.controller;

import com.filmreview.springbootproject.model.Film;
import com.filmreview.springbootproject.service.FilmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/home")
    public String findAll(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "home";
    }

    @GetMapping("/home/add-new-film")
    public String showAddFilm(Model model) {
        Film film = new Film();
        model.addAttribute("film",film);
        return "add_new_film";
    }

    @PostMapping("/home/add-new-film")
    public String addFilm(Model model,
                          @ModelAttribute("film") @Valid Film film) {
        filmService.save(film);
        return "redirect:/home";
    }
}
