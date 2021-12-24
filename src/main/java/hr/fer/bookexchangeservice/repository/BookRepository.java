package hr.fer.bookexchangeservice.repository;

import hr.fer.bookexchangeservice.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long>, JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {
}
