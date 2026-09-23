package com.loginseguro.controller;

import com.loginseguro.dto.CadastroUsuarioDTO;
import com.loginseguro.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CadastroController {

    private final UsuarioService usuarioService;

    public CadastroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/cadastro")
    public String exibirFormulario(Model model) {

        model.addAttribute("cadastroUsuarioDTO", new CadastroUsuarioDTO());

        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(
            @Valid CadastroUsuarioDTO cadastroUsuarioDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "cadastro";
        }

        try {
            usuarioService.cadastrar(cadastroUsuarioDTO);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue(
                    "email",
                    "email.duplicado",
                    e.getMessage()
            );

            return "cadastro";
        }

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Cadastro realizado com sucesso!"
        );

        return "redirect:/cadastro";
    }
}
