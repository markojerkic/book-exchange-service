package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.exception.BookNotFoundException;
import hr.fer.bookexchangeservice.exception.IsbnAlreadyExistsException;
import hr.fer.bookexchangeservice.model.entity.Author;
import hr.fer.bookexchangeservice.model.entity.Book;
import hr.fer.bookexchangeservice.model.entity.Genre;
import hr.fer.bookexchangeservice.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        if (this.bookRepository.existsByIsbn(book.getIsbn())) {
            throw new IsbnAlreadyExistsException("ISBN " + book.getIsbn() + " se već koristi");
        }
        return this.bookRepository.save(book);
    }

    @Secured("ROLE_ADMIN")
    public void deleteBook(Long id) {
        this.assertExists(id);
        this.bookRepository.deleteById(id);
    }

    public Page<Book> getPagedBooks(Pageable pageable, Optional<String> title, 
                                    Optional<String> isbn, Optional<Long> author) {
        return this.bookRepository.findAll(this.createQuerySpecification(title, isbn, author), pageable);
    }

    private Specification<Book> createQuerySpecification(Optional<String> title,
                                                         Optional<String> isbn, Optional<Long> author) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            title.ifPresent(t -> predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("title")),
                    "%" + t.toUpperCase() + "%")));
            isbn.ifPresent(isb -> predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("title")),
                    "%" + isb.toUpperCase() + "%")));
            author.ifPresent(authorId -> predicates.add(criteriaBuilder.equal(root.get("bookAuthor")
                    .get("id"), authorId)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Book getBookById(Long id) {
        return this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Knjiga " + id + " ne postoji"));
    }

    @Secured("ROLE_ADMIN")
    public Book updateById(Long id, Book book) {
        this.assertExists(id);
        book.setId(id);
        return this.bookRepository.save(book);
    }

    private void assertExists(Long id) {
        if (!this.bookRepository.existsById(id)) {
            throw new BookNotFoundException("Knjiga " + id + " nije pronađena");
        }
    }

    public List<Book> getBooksThatWroteGenre(Long id) {
        Genre genre = new Genre();
        genre.setId(id);
        return this.bookRepository.findBooksByGenresContaining(genre);
    }
}
