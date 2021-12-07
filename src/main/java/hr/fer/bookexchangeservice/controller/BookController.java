package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.entity.Book;
import hr.fer.bookexchangeservice.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/book")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("all")
    public List<Book> getALlBooks() {
        return this.bookService.getAllBooks();
    }
}
