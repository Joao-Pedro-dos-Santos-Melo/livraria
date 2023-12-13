package com.example.livraria.controller;

import com.example.livraria.entity.Author;
import com.example.livraria.service.AuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Log4j2
@Controller
@AllArgsConstructor
public class LivrariaController {

    private final AuthorService service;

    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping("/authors")
    public String author(Model model) {
        List<Author> authors = service.getAllAuthors();
        model.addAttribute("authors", authors);
        return "authors";
    }
}
