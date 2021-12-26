package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.entity.Book;
import hr.fer.bookexchangeservice.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/book")
@AllArgsConstructor
@CrossOrigin("*")
public class BookController {
    private final BookService bookService;

    @GetMapping("all")
    public List<Book> getALlBooks() {
        return this.bookService.getAllBooks();
    }

    @GetMapping("{id}")
    public Book getBookById(@PathVariable Long id) {
        return this.bookService.getBookById(id);
    }

    @PatchMapping("{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return this.bookService.updateById(id, book);
    }

    @GetMapping
    public Page<Book> getPagedAuthors(Pageable pageable) {
        return this.bookService.getPagedBooks(pageable);
    }

    @PostMapping
    public Book saveBook(@RequestBody Book book) {
        return this.bookService.saveBook(book);
    }
}
