package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.exception.BookNotFoundException;
import hr.fer.bookexchangeservice.exception.IsbnAlreadyExistsException;
import hr.fer.bookexchangeservice.model.entity.Book;
import hr.fer.bookexchangeservice.model.entity.Image;
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
    private final ImageService imageService;

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        if (this.bookRepository.existsByIsbn(book.getIsbn())) {
            throw new IsbnAlreadyExistsException("ISBN " + book.getIsbn() + " se već koristi");
        }
        Book savedBook = this.bookRepository.save(book);
        this.saveBookImages(book.getBookImages(), book.getId());
        return savedBook;
    }

    private void saveBookImages(List<Image> images, Long bookId) {
        Book book = new Book();
        book.setId(bookId);
        if (images != null) {
            images.stream().peek(image -> image.setBook(book)).forEach(this.imageService::updateImage);
        }
    }

    @Secured("ROLE_ADMIN")
    public void deleteBook(Long id) {
        this.assertExists(id);
        this.bookRepository.deleteById(id);
        this.imageService.deleteImageFilesByBookId(id);
    }

    public Page<Book> getPagedBooks(Pageable pageable, Optional<String> title,
                                    Optional<String> isbn, Optional<Long> author, Optional<Long> genre) {
        return this.bookRepository.findAll(this.createQuerySpecification(title, isbn, author, genre), pageable);
    }

    private Specification<Book> createQuerySpecification(Optional<String> title,
                                                         Optional<String> isbn,
                                                         Optional<Long> author,
                                                         Optional<Long> genre) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            title.ifPresent(t -> predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("title")),
                    "%" + t.toUpperCase() + "%")));
            isbn.ifPresent(isb -> predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("title")),
                    "%" + isb.toUpperCase() + "%")));
            author.ifPresent(authorId -> predicates.add(criteriaBuilder.equal(root.get("bookAuthor")
                    .get("id"), authorId)));
            genre.ifPresent(genreId -> predicates.add(criteriaBuilder.equal(root.join("genres")
                    .get("id"), genreId)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Book getBookById(Long id) {
        Book book = this.bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Knjiga " + id + " ne postoji"));
        return book;
    }

    @Secured("ROLE_ADMIN")
    public Book updateById(Long id, Book book) {
        this.assertExists(id);
        book.setId(id);
        Book savedBook = this.bookRepository.save(book);
        this.saveBookImages(book.getBookImages(), book.getId());
        return savedBook;
    }

    private void assertExists(Long id) {
        if (!this.bookRepository.existsById(id)) {
            throw new BookNotFoundException("Knjiga " + id + " nije pronađena");
        }
    }
}
