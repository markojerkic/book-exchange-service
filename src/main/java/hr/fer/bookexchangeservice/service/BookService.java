package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.model.entity.Book;
import hr.fer.bookexchangeservice.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Book> getPagedBooks(Pageable pageable) {
        return this.bookRepository.findAll(pageable);
    }
}
