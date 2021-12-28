package hr.fer.bookexchangeservice.repository;

import hr.fer.bookexchangeservice.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuthorRepository extends PagingAndSortingRepository<Author, Long>, JpaRepository<Author, Long>,
        JpaSpecificationExecutor<Author> {
}
