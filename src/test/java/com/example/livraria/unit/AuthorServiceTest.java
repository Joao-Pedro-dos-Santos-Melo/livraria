package com.example.livraria.unit;

import com.example.livraria.entity.Author;
import com.example.livraria.entity.Book;
import com.example.livraria.exception.AuthorAlreadyExistException;
import com.example.livraria.exception.AuthorNotFoundException;
import com.example.livraria.exception.InvalidAuthorException;
import com.example.livraria.repository.AuthorRepository;
import com.example.livraria.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @InjectMocks
    private AuthorService service;

    @Mock
    private AuthorRepository repository;

    @Test
    @DisplayName("#findById > When the id os null > throw an exception")
    void findByWhenTheIdIsNullThrowAnException(){
        assertThrows(IllegalArgumentException.class, () -> service.findById(null));
    }

    @Test
    @DisplayName("#findById > When the id is not null > When no author is found > Return Throw an exception")
    void findByIdWhenIdIsNotNullWhenNoAuthorIsFoundThrowAnException(){
        when(repository.findById(2)).thenThrow(new AuthorNotFoundException());
        assertThrows(AuthorNotFoundException.class, () -> service.findById(2));
    }

    @Test
    @DisplayName("#findBId > When the id is not null > When author is found > Return Author")
    void findByIdWhenAuthorIsFoundReturnAuthor(){
        when(repository.findById(1)).thenReturn(Optional.of(new Author(1, "joao", "pedro", "mineiro", null)));
        Author result = service.findById(1);
        assertAll(
                () -> Assertions.assertEquals(1, result.getId()),
                () -> Assertions.assertEquals("joao", result.getFirstName()),
                () -> Assertions.assertEquals("pedro", result.getLastName()),
                () -> Assertions.assertEquals("mineiro", result.getNationality()),
                () -> Assertions.assertEquals(null, result.getBooks())
        );

    }

    @Test
    @DisplayName("#add > Quando algum dos parametros for null > Retorne um erro")
    void addWhenTheParametricsIsNullReturnoThrows(){

        Book book1 = Book.builder()
                .title("Livro 1")
                .publicationYear(2023)
                .pages(200)
                .build();

        Book book2 = Book.builder()
                .title("Livro 2")
                .publicationYear(2024)
                .pages(250)
                .build();
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        Author author = null;
        assertAll(
                () -> assertThrows(InvalidAuthorException.class, () ->{service.add(Author.builder()
                        .firstName(null)
                        .lastName("pedro")
                        .nationality("brasileiro")
                        .books(books)
                        .build());}),
                () -> assertThrows(InvalidAuthorException.class, () ->{ service.add(author);}),
                () -> assertThrows(InvalidAuthorException.class, () ->{ service.add(Author.builder()
                        .firstName("joao")
                        .lastName(null)
                        .nationality("brasileiro")
                        .books(books)
                        .build());}),
                () -> assertThrows(InvalidAuthorException.class, () ->{ service.add(Author.builder()
                        .firstName("joao")
                        .lastName("pedro")
                        .nationality(null)
                        .books(books)
                        .build());})
        );
    }

    @Test
    @DisplayName("#add > Quando auhtor é valido > Mas ja esta cadastrado > Retorne Exceção.")
    void addAuthorIsValidWhenIsExistInDBRetornExcepetion(){
        Book book1 = Book.builder()
                .title("Livro 1")
                .publicationYear(2023)
                .pages(200)
                .build();

        Book book2 = Book.builder()
                .title("Livro 2")
                .publicationYear(2024)
                .pages(250)
                .build();
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        Author author = Author.builder()
                .id(9)
                .firstName("null")
                .lastName("pedro")
                .nationality("brasileiro")
                .books(books)
                .build();
        Mockito.when(repository.existsById(author.getId())).thenThrow(new AuthorAlreadyExistException());
        assertThrows(AuthorAlreadyExistException.class, () -> service.add(author));
    }

    @Test
    @DisplayName("#add > Quando os parametros estao ok > author não existe > adicione o author")
    void addTeste3(){
        Book book1 = Book.builder()
                .title("Livro 1")
                .publicationYear(2023)
                .pages(200)
                .build();

        Book book2 = Book.builder()
                .title("Livro 2")
                .publicationYear(2024)
                .pages(250)
                .build();
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        Author author = Author.builder()
                .id(9)
                .firstName("null")
                .lastName("pedro")
                .nationality("brasileiro")
                .books(books)
                .build();
        Mockito.when(repository.existsById(author.getId())).thenReturn(false);
        Mockito.when(repository.save(author)).thenReturn(author);
        Author result = service.add(author);
        assertAll(
                () -> Assertions.assertEquals("null", result.getFirstName()),
                () -> Assertions.assertEquals("pedro", result.getLastName()),
                () -> Assertions.assertEquals("brasileiro", result.getNationality()),
                () -> Assertions.assertEquals(2, result.getBooks().size())

        );
    }

    @Test
    @DisplayName("#deleteById > quando o author não existe > Retorne um exceção")
    void deleteByIdTeste1(){
        Mockito.when(repository.existsById(9)).thenThrow(new AuthorNotFoundException());
        assertThrows(AuthorNotFoundException.class, () -> service.deleteById(9));
    }


    @Test
    @DisplayName("#update > Quando algum dos parametros for null > Retorne um erro")
    void updateWhenTheParametricsIsNullReturnoThrows(){

        Book book1 = Book.builder()
                .title("Livro 1")
                .publicationYear(2023)
                .pages(200)
                .build();

        Book book2 = Book.builder()
                .title("Livro 2")
                .publicationYear(2024)
                .pages(250)
                .build();
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        Author author = null;
        assertAll(
                () -> assertThrows(InvalidAuthorException.class, () ->{service.update(Author.builder()
                        .firstName(null)
                        .lastName("pedro")
                        .nationality("brasileiro")
                        .books(books)
                        .build());}),
                () -> assertThrows(InvalidAuthorException.class, () ->{ service.update(author);}),
                () -> assertThrows(InvalidAuthorException.class, () ->{ service.update(Author.builder()
                        .firstName("joao")
                        .lastName(null)
                        .nationality("brasileiro")
                        .books(books)
                        .build());}),
                () -> assertThrows(InvalidAuthorException.class, () ->{ service.update(Author.builder()
                        .firstName("joao")
                        .lastName("pedro")
                        .nationality(null)
                        .books(books)
                        .build());})
        );
    }

    @Test
    @DisplayName("#update > Quando auhtor é valido > Não existe > Retorne Exceção.")
    void updateAuthorIsValidWhenNotExistInDBRetornExcepetion(){
        Book book1 = Book.builder()
                .title("Livro 1")
                .publicationYear(2023)
                .pages(200)
                .build();

        Book book2 = Book.builder()
                .title("Livro 2")
                .publicationYear(2024)
                .pages(250)
                .build();
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        Author author = Author.builder()
                .id(9)
                .firstName("null")
                .lastName("pedro")
                .nationality("brasileiro")
                .books(books)
                .build();
        Mockito.when(repository.existsById(author.getId())).thenThrow(new AuthorNotFoundException());
        assertThrows(AuthorNotFoundException.class, () -> service.update(author));
    }

    @Test
    @DisplayName("#add > Quando os parametros estao ok > author existe > atualize os dados")
    void updateTeste3(){
        Book book1 = Book.builder()
                .title("Livro 1")
                .publicationYear(2023)
                .pages(200)
                .build();

        Book book2 = Book.builder()
                .title("Livro 2")
                .publicationYear(2024)
                .pages(250)
                .build();
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        Author author = Author.builder()
                .id(9)
                .firstName("null")
                .lastName("pedro")
                .nationality("brasileiro")
                .books(books)
                .build();
        Mockito.when(repository.existsById(author.getId())).thenReturn(true);
        Mockito.when(repository.save(author)).thenReturn(author);
        Author result = service.update(author);
        assertAll(
                () -> Assertions.assertEquals("null", result.getFirstName()),
                () -> Assertions.assertEquals("pedro", result.getLastName()),
                () -> Assertions.assertEquals("brasileiro", result.getNationality()),
                () -> Assertions.assertEquals(2, result.getBooks().size())

        );
    }


}
