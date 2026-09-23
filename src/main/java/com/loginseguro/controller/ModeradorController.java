package com.loginseguro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModeradorController {

    @GetMapping("/moderador")
    public String exibirAreaModerador() {
        return "moderador";
    }
}
