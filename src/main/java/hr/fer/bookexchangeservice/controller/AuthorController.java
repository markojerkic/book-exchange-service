package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.entity.Author;
import hr.fer.bookexchangeservice.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/author")
@AllArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("all")
    public List<Author> getAllAuthors() {
        return this.authorService.getAllAuthors();
    }

    @GetMapping
    public Page<Author> getPagedAuthors(Pageable pageable, @RequestParam Optional<String> firstName,
                                        @RequestParam Optional<String> lastName,
                                        @RequestParam Optional<Long> yearOfBirth,
                                        @RequestParam Optional<Long> yearOfDeath) {
        return this.authorService.getPagedAuthors(pageable, firstName, lastName, yearOfBirth, yearOfDeath);
    }

    @PostMapping
    public Author saveAuthor(@RequestBody Author author) {
        return this.authorService.saveAuthor(author);
    }
}
