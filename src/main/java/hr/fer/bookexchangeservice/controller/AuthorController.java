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
@CrossOrigin("*")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("all")
    public List<Author> getAllAuthors() {
        return this.authorService.getAllAuthors();
    }

    @GetMapping("{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return this.authorService.getAuthorById(id);
    }

    @PatchMapping("{id}")
    public Author updateAdvert(@PathVariable Long id, @RequestBody Author author) {
        return this.authorService.updateById(id, author);
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

    @DeleteMapping("{id}")
    public void deleteAuthorById(@PathVariable Long id) {
        this.authorService.deleteAuthor(id);
    }
}
