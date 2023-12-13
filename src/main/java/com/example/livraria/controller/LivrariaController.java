package com.example.livraria.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
@AllArgsConstructor
public class LivrariaController {

    private final LivrariaService service;
}
