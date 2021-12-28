package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.exception.AuthorNotFoundException;
import hr.fer.bookexchangeservice.model.entity.Author;
import hr.fer.bookexchangeservice.model.entity.Genre;
import hr.fer.bookexchangeservice.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Secured("ROLE_ADMIN")
    public void deleteAuthor(Long id) {
        this.assertExists(id);
        this.authorRepository.deleteById(id);
    }

    public Page<Author> getPagedAuthors(Pageable pageable, Optional<String> firstName,
                                        Optional<String> lastName, Optional<Long> yearOfBirth,
                                        Optional<Long> yearOfDeath) {
        return this.authorRepository.findAll(this.createQuerySpecification(firstName, lastName, yearOfBirth,
                yearOfDeath), pageable);
    }

    private Specification<Author> createQuerySpecification(Optional<String> firstName,
                                                           Optional<String> lastName, Optional<Long> yearOfBirth,
                                                           Optional<Long> yearOfDeath) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            firstName.ifPresent(name -> predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("firstName")),
                    "%" + name.toUpperCase() + "%")));
            lastName.ifPresent(name -> predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("lastName")),
                    "%" + name.toUpperCase() + "%")));
            yearOfBirth.ifPresent(year -> predicates.add(criteriaBuilder.equal(criteriaBuilder.function("YEAR",
                            Integer.class, root.get("yearOfBirth")),
                    (new Date(year)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear())));
            yearOfDeath.ifPresent(year -> predicates.add(criteriaBuilder.equal(criteriaBuilder.function("YEAR",
                            Integer.class, root.get("yearOfDeath")),
                    (new Date(year)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear())));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Author getAuthorById(Long id) {
        return this.authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Autor " + id + " nije pronađen"));
    }

    @Secured("ROLE_ADMIN")
    public Author updateById(Long id, Author author) {
        this.assertExists(id);
        author.setId(id);
        return this.authorRepository.save(author);
    }

    public void assertExists(Long id) {
        if (!this.authorRepository.existsById(id)) {
            throw new AuthorNotFoundException("Autor " + id + " nije pronađen");
        }
    }

    public List<Author> getAuthorThatWroteGenre(Long genreId) {
        Genre genre = new Genre();
        genre.setId(genreId);
        return this.authorRepository.findAuthorByAuthorsGenresContains(genre);
    }
}
