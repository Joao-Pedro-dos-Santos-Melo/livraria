package com.example.livraria.controller;

import com.example.livraria.entity.Author;
import com.example.livraria.entity.Book;
import com.example.livraria.service.AuthorService;
import com.example.livraria.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@Controller
@AllArgsConstructor
public class LivrariaController {

    private final AuthorService service;
    private final BookService serviceBook;

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

    @GetMapping("/author")
    public String author(Author author) {
        return "newauthor";
    }

    @PostMapping("/author")
    public String newAuthor(@ModelAttribute("author") Author author) {
        // TODO: Add the new Author
        // service.add || service.save
        log.info("Entrou no cadastro de usuário");
        Author addedAuthor = service.add(author);
        return "redirect:/author/" + addedAuthor.getId();
    }

    @GetMapping("/author/{id}")
    public String showAuthor(@PathVariable("id") Integer id,
                           Model model) {
        Author author = service.findById(id);
        model.addAttribute("author", author);
        return "showauthor";
    }

    //books
    @GetMapping("/books")
    public String book(Model model) {
        List<Book> books = serviceBook.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/book")
    public String book(Author book) {
        return "newbook";
    }


    @PostMapping("/book")
    public String newBook(@ModelAttribute("book") Book book) {
        // TODO: Add the new Book
        // service.add || service.save
        log.info("Entrou no cadastro de livro");
        Book addedBook = serviceBook.add(book);
        return "redirect:/book/" + addedBook.getId();
    }
}
