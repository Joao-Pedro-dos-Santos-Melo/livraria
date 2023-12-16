package com.example.livraria.controller;

import com.example.livraria.entity.Author;
import com.example.livraria.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService service;

    @GetMapping("/")
    public ResponseEntity<List<Author>> findAllAuthors() {
        List<Author> authors = service.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthorById(@PathVariable Integer id) {
        service.deleteById(id);
    }


}
