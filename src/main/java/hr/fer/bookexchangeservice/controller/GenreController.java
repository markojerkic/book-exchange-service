package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.entity.Genre;
import hr.fer.bookexchangeservice.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/genre")
@AllArgsConstructor
@CrossOrigin("*")
public class GenreController {
    private final GenreService genreService;

    @GetMapping("all")
    public List<Genre> getAllGenres() {
        return this.genreService.getAllGenres();
    }

    @GetMapping("{id}")
    public Genre getGenreById(@PathVariable Long id) {
        return this.genreService.getGenreById(id);
    }

    @PatchMapping("{id}")
    public Genre updateGenre(@PathVariable Long id, @RequestBody Genre genre) {
        return this.genreService.updateById(id, genre);
    }

    @GetMapping
    public Page<Genre> getPagedGenres(Pageable pageable) {
        return this.genreService.getPagedGenres(pageable);
    }

    @PostMapping
    public Genre saveGenre(@RequestBody Genre genre) {
        return this.genreService.saveGenre(genre);
    }
}
