package com.loginseguro.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping("/inicio")
    public String exibirInicio(
            Authentication authentication,
            Model model) {

        model.addAttribute(
                "emailUsuario",
                authentication.getName()
        );

        return "inicio";
    }
}
