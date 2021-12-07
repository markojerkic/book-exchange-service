package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.model.entity.Book;
import hr.fer.bookexchangeservice.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }
}
