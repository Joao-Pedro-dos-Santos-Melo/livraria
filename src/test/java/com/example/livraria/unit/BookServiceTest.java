package com.example.livraria.unit;

import com.example.livraria.entity.Author;
import com.example.livraria.entity.Book;
import com.example.livraria.exception.AuthorNotFoundException;
import com.example.livraria.exception.BookNotFoundException;
import com.example.livraria.exception.InvalidAuthorException;
import com.example.livraria.repository.AuthorRepository;
import com.example.livraria.repository.BookRepository;
import com.example.livraria.service.AuthorService;
import com.example.livraria.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService service;

    @Mock
    private BookRepository repository;

    @Test
    @DisplayName("#findById > When the id os null > throw an exception")
    void findByWhenTheIdIsNullThrowAnException(){
        assertThrows(IllegalArgumentException.class, () -> service.findById(null));
    }

    @Test
    @DisplayName("#findById > When the id is not null > When no book is found > Return Throw an exception")
    void findByIdWhenIdIsNotNullWhenNoBookIsFoundThrowAnException(){
        when(repository.findById(2)).thenThrow(new BookNotFoundException());
        assertThrows(BookNotFoundException.class, () -> service.findById(2));
    }

    @Test
    @DisplayName("#findBId > When the id is not null > When book is found > Return Book")
    void findByIdWhenBookIsFoundReturnBook(){
        when(repository.findById(1)).thenReturn(Optional.of(new Book(1, "Narnia", 1997, 360, null)));
        Book result = service.findById(1);
        assertAll(
                () -> Assertions.assertEquals(1, result.getId()),
                () -> Assertions.assertEquals("Narnia", result.getTitle()),
                () -> Assertions.assertEquals(1997, result.getPublicationYear()),
                () -> Assertions.assertEquals(360, result.getPages()),
                () -> Assertions.assertEquals(null, result.getAuthor())
        );

    }

    @Test
    @DisplayName("#add > quando os parametros são null > Retorne uma exceção")
    void addTeste1(){
        Book book = null;
        Author author1 = Author.builder()
                .id(9)
                .firstName("joao")
                .lastName("pedro")
                .nationality("brasileiro")
                .books(null)
                .build();
        Author author2 = null;
        assertAll(
                () -> assertThrows(InvalidAuthorException.class, () ->{service.add(book);}),
                () -> assertThrows(InvalidAuthorException.class, () ->{service.add(Book.builder()
                        .title(null)
                        .publicationYear(1997)
                        .pages(360)
                        .author(author1).build());}),
                () -> assertThrows(InvalidAuthorException.class, () ->{service.add(Book.builder()
                        .title("Narnia")
                        .publicationYear(null)
                        .pages(360)
                        .author(author1).build());}),
                () -> assertThrows(InvalidAuthorException.class, () ->{service.add(Book.builder()
                        .title("Narnia")
                        .publicationYear(1997)
                        .pages(null)
                        .author(author1).build());}),
                () -> assertThrows(InvalidAuthorException.class, () ->{service.add(Book.builder()
                        .title("Narnia")
                        .publicationYear(1997)
                        .pages(360)
                        .author(author2).build());})
        );
    }

    @Test
    @DisplayName("#add > quando os parametros não sao null > add o livro")
    void addTeste2(){
        Author author1 = Author.builder()
                .id(9)
                .firstName("joao")
                .lastName("pedro")
                .nationality("brasileiro")
                .books(null)
                .build();
        Book book = Book.builder()
                .title("Narnia")
                .publicationYear(1997)
                .pages(360)
                .author(author1).build();
        Mockito.when(repository.save(book)).thenReturn(book);
        Book result = service.add(book);
        assertAll(
                () -> Assertions.assertEquals("Narnia", result.getTitle()),
                () -> Assertions.assertEquals(1997, result.getPublicationYear()),
                () -> Assertions.assertEquals(360, result.getPages()),
                () -> Assertions.assertEquals(author1.getId(), result.getAuthor().getId())
        );
    }

    @Test
    @DisplayName("#deleteById > quando o author não existe > Retorne um exceção")
    void deleteByIdTeste1(){
        Mockito.when(repository.existsById(9)).thenThrow(new BookNotFoundException());
        assertThrows(BookNotFoundException.class, () -> service.deleteById(9));
    }

    @Test
    @DisplayName("#update > quando os parametros são null > Retorne uma exceção")
    void updateTeste1(){
        Book book = null;
        Author author1 = Author.builder()
                .id(9)
                .firstName("joao")
                .lastName("pedro")
                .nationality("brasileiro")
                .books(null)
                .build();
        Author author2 = null;
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () ->{service.add(book);}),
                () -> assertThrows(IllegalArgumentException.class, () ->{service.add(Book.builder()
                        .id(26)
                        .title(null)
                        .publicationYear(1997)
                        .pages(360)
                        .author(author1).build());}),
                () -> assertThrows(IllegalArgumentException.class, () ->{service.add(Book.builder()
                        .id(26)
                        .title("Narnia")
                        .publicationYear(null)
                        .pages(360)
                        .author(author1).build());}),
                () -> assertThrows(IllegalArgumentException.class, () ->{service.add(Book.builder()
                        .id(26)
                        .title("Narnia")
                        .publicationYear(1997)
                        .pages(null)
                        .author(author1).build());}),
                () -> assertThrows(IllegalArgumentException.class, () ->{service.add(Book.builder()
                        .id(26)
                        .title("Narnia")
                        .publicationYear(1997)
                        .pages(360)
                        .author(author2).build());})
        );
    }

    @Test
    @DisplayName("#add > quando os parametros não sao null > mas o livro não existe > Retorne exceção")
    void updateTeste2(){
        Author author1 = Author.builder()
                .id(9)
                .firstName("joao")
                .lastName("pedro")
                .nationality("brasileiro")
                .books(null)
                .build();
        Book book = Book.builder()
                .id(26)
                .title("Narnia")
                .publicationYear(1997)
                .pages(360)
                .author(author1).build();
        Mockito.when(repository.existsById(book.getId())).thenReturn(false);
        assertThrows(BookNotFoundException.class, () -> service.update(book));

    }

    @Test
    @DisplayName("#add > quando os parametros não sao null > o livro existe > atualize o livro")
    void updateTeste3(){
        Author author1 = Author.builder()
                .id(9)
                .firstName("joao")
                .lastName("pedro")
                .nationality("brasileiro")
                .books(null)
                .build();
        Book book = Book.builder()
                .id(26)
                .title("Narnia")
                .publicationYear(1997)
                .pages(360)
                .author(author1).build();
        Mockito.when(repository.existsById(book.getId())).thenReturn(true);
        Mockito.when(repository.save(book)).thenReturn(book);
        Book result = service.update(book);
        assertAll(
                () -> Assertions.assertEquals(26, result.getId()),
                () -> Assertions.assertEquals("Narnia", result.getTitle()),
                () -> Assertions.assertEquals(1997, result.getPublicationYear()),
                () -> Assertions.assertEquals(360, result.getPages()),
                () -> Assertions.assertEquals(author1.getId(), result.getAuthor().getId())
        );
    }

}
