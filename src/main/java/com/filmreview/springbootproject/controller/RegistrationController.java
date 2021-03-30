package com.filmreview.springbootproject.controller;

import com.filmreview.springbootproject.service.RegistrationRequest;
import com.filmreview.springbootproject.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
}
