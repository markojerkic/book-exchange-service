package hr.fer.bookexchangeservice.repository;

import hr.fer.bookexchangeservice.model.entity.Author;
import hr.fer.bookexchangeservice.model.entity.Book;
import hr.fer.bookexchangeservice.service.AuthorService;
import hr.fer.bookexchangeservice.service.BookService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BookServiceTest {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    private BookService bookService;
    private AuthorService authorService;

    @BeforeEach()
    public void setup() {
        this.bookService = new BookService(bookRepository);
        this.authorService = new AuthorService(authorRepository);
    }

    @Test
    public void testAddNewBookWithSavedAuthor() {
        Author author = new Author();
        author.setFirstName("Ivo");
        author.setLastName("Andrić");
        author.setYearOfBirth(new Date());

        Author savedAuthor = this.authorService.saveAuthor(author);

        Book book = new Book();
        Author authorToBeSaved = new Author();
        authorToBeSaved.setId(savedAuthor.getId());
        book.setBookAuthor(authorToBeSaved);
        book.setISBN("lskdjf");
        book.setTitle("Prokleta avlija");

        Book savedBook = this.bookService.saveBook(book);

        assertEquals(savedAuthor.getId(), savedBook.getBookAuthor().getId());

    }
}
