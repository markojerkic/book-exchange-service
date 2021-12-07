package hr.fer.bookexchangeservice.repository;

import hr.fer.bookexchangeservice.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface GenreRepository extends PagingAndSortingRepository<Genre, Long>, JpaRepository<Genre, Long> {
}
