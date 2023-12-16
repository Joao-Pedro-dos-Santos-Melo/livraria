package com.example.livraria.service;

import com.example.livraria.controller.request.AuthorRequest;
import com.example.livraria.entity.Author;
import com.example.livraria.exception.AuthorAlreadyExistException;
import com.example.livraria.exception.AuthorNotFoundException;
import com.example.livraria.exception.InvalidAuthorException;
import com.example.livraria.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
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

    public Author add(Author author){
        if (Objects.isNull(author) || Objects.isNull(author.getFirstName())
                || Objects.isNull(author.getLastName()) || Objects.isNull(author.getNationality())) {
            throw new InvalidAuthorException("Parametros invalidos para adicionar author.");
        }
        if(repository.existsById(author.getId())){
            throw new AuthorAlreadyExistException("Author ja foi adicionado.");
        }else{
            return repository.save(author);
        }

    }

    public Author add(AuthorRequest request) {
        Author author = Author.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .nationality(request.getNationality())
                .build();
        return add(author);
    }

    public void deleteById(Integer id){
        if(repository.existsById(id)){
            repository.deleteById(id);
        }else{
            throw new AuthorNotFoundException("Este author não existe do banco de dados.");
        }

    }

    public Author update(Author author){
        if (Objects.isNull(author) || Objects.isNull(author.getFirstName())
                || Objects.isNull(author.getLastName()) || Objects.isNull(author.getNationality())) {
            throw new InvalidAuthorException("Parametros invalidos para adicionar author.");
        }
        if(repository.existsById(author.getId())){
            return repository.save(author);
        }else{
            throw new AuthorNotFoundException("Este author não existe do banco de dados.");
        }
    }


}
