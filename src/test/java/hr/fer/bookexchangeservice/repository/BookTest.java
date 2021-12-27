package hr.fer.bookexchangeservice.repository;

import hr.fer.bookexchangeservice.model.entity.Author;
import hr.fer.bookexchangeservice.model.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookTest {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveBookWithSavedAuthor() {
        Author author = new Author();
        author.setFirstName("Ivo");
        author.setLastName("Andrić");
        author.setYearOfBirth(new Date());

        Author savedAuthor = this.authorRepository.save(author);

        Book book = new Book();
        Author authorToBeSaved = new Author();
        authorToBeSaved.setId(savedAuthor.getId());
        book.setBookAuthor(authorToBeSaved);
        book.setISBN("lskdjf");
        book.setTitle("Prokleta avlija");

        Book savedBook = this.bookRepository.save(book);

        assertEquals(savedAuthor.getId(), savedBook.getBookAuthor().getId());
    }

    @Test
    public void testDeleteCascade() {
        Author author = new Author();
        author.setFirstName("Ivo");
        author.setLastName("Andrić");
        author.setYearOfBirth(new Date());

        Author savedAuthor = this.authorRepository.save(author);

        Book book = new Book();
        Author authorToBeSaved = new Author();
        authorToBeSaved.setId(savedAuthor.getId());
        book.setBookAuthor(authorToBeSaved);
        book.setISBN("lskdjf");
        book.setTitle("Prokleta avlija");

        Book savedBook = this.bookRepository.save(book);

        this.authorRepository.deleteById(savedAuthor.getId());
        assertEquals(Optional.empty(), authorRepository.findById(savedAuthor.getId()));
        assertEquals(Optional.empty(), bookRepository.findById(savedBook.getId()));
        assertNull(savedBook.getBookAuthor());

    }
}
