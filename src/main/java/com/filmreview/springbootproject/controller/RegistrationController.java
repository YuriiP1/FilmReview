package com.filmreview.springbootproject.controller;

import com.filmreview.springbootproject.model.RegistrationRequest;
import com.filmreview.springbootproject.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String showRegistration(Model model) {
        RegistrationRequest request = new RegistrationRequest();
        model.addAttribute("request", request);
        return "registration";
    }

    @PostMapping
    public String registration(@ModelAttribute("request") RegistrationRequest request) {

        registrationService.register(request);

        return "login";
    }
}
