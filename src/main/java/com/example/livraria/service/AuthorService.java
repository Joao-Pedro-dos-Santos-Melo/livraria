package com.example.livraria.service;

import com.example.livraria.entity.Author;
import com.example.livraria.exception.AuthorNotFoundException;
import com.example.livraria.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;

    public List<Author> getAllAuthors() {
        return repository.findAll();
    }

    public Author findById(Integer id){
        if (Objects.isNull(id)){
            throw new IllegalArgumentException("Id null when fetching for an author.");
        }
        return repository.findById(id).orElseThrow(() -> new AuthorNotFoundException(String.format("No author found id %d", id)));
    }


}
