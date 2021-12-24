package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.model.entity.Genre;
import hr.fer.bookexchangeservice.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return this.genreRepository.findAll();
    }

    public Genre saveGenre(Genre genre) {
        return this.genreRepository.save(genre);
    }

    public Page<Genre> getPagedGenres(Pageable pageable) {
        return this.genreRepository.findAll(pageable);
    }
}
