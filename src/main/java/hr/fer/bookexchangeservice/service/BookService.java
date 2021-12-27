package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.exception.AuthorNotFoundException;
import hr.fer.bookexchangeservice.exception.BookNotFoundException;
import hr.fer.bookexchangeservice.model.entity.Book;
import hr.fer.bookexchangeservice.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return this.bookRepository.save(book);
    }

    @Secured("ROLE_ADMIN")
    public void deleteBook(Long id) {
        this.assertExists(id);
        this.bookRepository.deleteById(id);
    }

    public Page<Book> getPagedBooks(Pageable pageable) {
        return this.bookRepository.findAll(pageable);
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
            throw new AuthorNotFoundException("Knjiga " + id + " nije pronađena");
        }
    }
}
