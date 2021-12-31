package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.exception.AuthorNotFoundException;
import hr.fer.bookexchangeservice.model.entity.Author;
import hr.fer.bookexchangeservice.model.entity.Image;
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
    private final ImageService imageService;

    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }

    public Author saveAuthor(Author author) {
        Author savedAuthor = this.authorRepository.save(author);
        this.saveAuthorImages(author.getAuthorImages(), author.getId());
        return savedAuthor;
    }

    private void saveAuthorImages(List<Image> images, Long authorId) {
        Author author = new Author();
        author.setId(authorId);
        images.stream().peek(image -> image.setAuthor(author)).forEach(this.imageService::updateImage);
    }

    @Secured("ROLE_ADMIN")
    public void deleteAuthor(Long id) {
        this.assertExists(id);
        this.authorRepository.deleteById(id);
        this.imageService.deleteImageFilesByAuthorId(id);
    }

    public Page<Author> getPagedAuthors(Pageable pageable, Optional<String> firstName,
                                        Optional<String> lastName, Optional<Long> yearOfBirth,
                                        Optional<Long> yearOfDeath, Optional<Long> genre) {
        return this.authorRepository.findAll(this.createQuerySpecification(firstName, lastName, yearOfBirth,
                yearOfDeath, genre), pageable);
    }

    private Specification<Author> createQuerySpecification(Optional<String> firstName,
                                                           Optional<String> lastName, Optional<Long> yearOfBirth,
                                                           Optional<Long> yearOfDeath, Optional<Long> genre) {
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
            genre.ifPresent(genreId -> predicates.add(criteriaBuilder.equal(root.join("authorsGenres")
                    .get("id"), genreId)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Author getAuthorById(Long id) {
        Author author = this.authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Autor " + id + " nije pronađen"));
        return author;
    }

    @Secured("ROLE_ADMIN")
    public Author updateById(Long id, Author author) {
        this.assertExists(id);
        author.setId(id);
        Author savedAuthor = this.authorRepository.save(author);
        this.saveAuthorImages(author.getAuthorImages(), author.getId());
        return savedAuthor;
    }

    public void assertExists(Long id) {
        if (!this.authorRepository.existsById(id)) {
            throw new AuthorNotFoundException("Autor " + id + " nije pronađen");
        }
    }
}
