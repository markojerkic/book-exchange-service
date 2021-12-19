package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.entity.Genre;
import hr.fer.bookexchangeservice.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/genre")
@AllArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("all")
    public List<Genre> getAllGenres() {
        return this.genreService.getAllGenres();
    }

    @PostMapping
    public Genre saveGenre(@RequestBody Genre genre) {
        return this.genreService.saveGenre(genre);
    }
}
