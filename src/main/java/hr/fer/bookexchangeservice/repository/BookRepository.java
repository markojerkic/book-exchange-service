package hr.fer.bookexchangeservice.repository;

import hr.fer.bookexchangeservice.model.entity.Book;
import hr.fer.bookexchangeservice.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book, Long>, JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {
    boolean existsByIsbn(String isbn);
    List<Book> findBooksByGenresContaining(Genre genre);
}
