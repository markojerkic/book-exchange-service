package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.exception.GenreNotFoundException;
import hr.fer.bookexchangeservice.model.entity.Genre;
import hr.fer.bookexchangeservice.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
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

    public Genre getGenreById(Long id) {
        return this.genreRepository.findById(id).orElseThrow(() -> new GenreNotFoundException("Žanr " + id + " nije pronađen"));
    }

    @Secured("ROLE_ADMIN")
    public Genre updateById(Long id, Genre genre) {
        this.assertExists(id);
        genre.setId(id);
        return this.genreRepository.save(genre);
    }

    @Secured("ROLE_ADMIN")
    public void deleteGenre(Long id) {
        this.assertExists(id);
        this.genreRepository.deleteById(id);
    }

    private void assertExists(Long id) {
        if (!this.genreRepository.existsById(id)) {
            throw new GenreNotFoundException("Žanr " + id + " nije pronađen");
        }
    }
}
