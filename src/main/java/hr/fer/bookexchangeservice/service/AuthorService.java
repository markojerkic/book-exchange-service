package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.model.entity.Author;
import hr.fer.bookexchangeservice.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }

    public Author saveAuthor(Author author) {
        return this.authorRepository.save(author);
    }
}
