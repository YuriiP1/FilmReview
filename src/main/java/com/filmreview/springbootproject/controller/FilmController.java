package com.filmreview.springbootproject.controller;

import com.filmreview.springbootproject.model.Film;
import com.filmreview.springbootproject.service.FilmService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/home/add-new-film")
    public String showAddFilm(Model model) {
        Film film = new Film();
        model.addAttribute("film",film);
        return "add_new_film";
    }

    @PostMapping("/home/add-new-film")
    public String addFilm(Model model,
                          @ModelAttribute("film") @Valid Film film,
                          BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "add_new_film";
        }
        filmService.save(film);
        return "redirect:/home";
    }
}
