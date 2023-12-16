package com.example.livraria.service;

import com.example.livraria.controller.request.BookRequest;
import com.example.livraria.entity.Author;
import com.example.livraria.entity.Book;
import com.example.livraria.exception.AuthorNotFoundException;
import com.example.livraria.exception.BookNotFoundException;
import com.example.livraria.exception.InvalidAuthorException;
import com.example.livraria.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository repository;

    private final AuthorService auhtorservice;

    public List<Book> getAllBooks(){return repository.findAll();}

    public Book add(Book book){
        if (Objects.isNull(book) || Objects.isNull(book.getTitle())
                || Objects.isNull(book.getPublicationYear()) || Objects.isNull(book.getPages()) || Objects.isNull(book.getAuthor())) {
            throw new InvalidAuthorException("Parametros invalidos para adicionar author.");
        }
        return repository.save(book);
    }

    public Book add(BookRequest request) {

        Author author = auhtorservice.findById(request.getIdAuthor());

        Book book = Book.builder()
                .title(request.getTitle())
                .publicationYear(request.getPublicationYear())
                .pages(request.getPages())
                .author(author)
                .build();
        return add(book);
    }

    public Book findById(Integer id){
        if (Objects.isNull(id)){
            throw new IllegalArgumentException("Id null when fetching for an book.");
        }
        return repository.findById(id).orElseThrow(() -> new BookNotFoundException(String.format("No book found id %d", id)));
    }

    public void deleteById(Integer id){
        if(repository.existsById(id)){
            repository.deleteById(id);
        }else{
            throw new BookNotFoundException("Este author não existe do banco de dados.");
        }

    }

    public Book update(Book book){
        if (Objects.isNull(book) || Objects.isNull(book.getId()) || Objects.isNull(book.getTitle())
                || Objects.isNull(book.getPublicationYear()) || Objects.isNull(book.getPages()) || Objects.isNull(book.getAuthor())) {
            throw new IllegalArgumentException("Parametros invalidos para adicionar author.");
        }
        if(repository.existsById(book.getId())){
            return repository.save(book);
        }else{
            throw new BookNotFoundException("Este author não existe do banco de dados.");
        }
    }
}
