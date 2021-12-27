package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.exception.GenreNotFoundException;
import hr.fer.bookexchangeservice.model.entity.Genre;
import hr.fer.bookexchangeservice.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Page<Genre> getPagedGenres(Pageable pageable, Optional<String> name,
                                      Optional<String> description) {
        return this.genreRepository.findAll(this.createQuerySpecification(name, description), pageable);
    }

    private Specification<Genre> createQuerySpecification(Optional<String> name,
                                                         Optional<String> description) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            name.ifPresent(n -> predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")),
                    "%" + n.toUpperCase() + "%")));
            description.ifPresent(d -> predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("description")),
                    "%" + d.toUpperCase() + "%")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
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
