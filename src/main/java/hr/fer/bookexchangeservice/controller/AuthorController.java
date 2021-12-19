package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.entity.Author;
import hr.fer.bookexchangeservice.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/author")
@AllArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("all")
    public List<Author> getAllAuthors() {
        return this.authorService.getAllAuthors();
    }

    @PostMapping
    public Author saveAuthor(@RequestBody Author author) {
        return this.authorService.saveAuthor(author);
    }
}
