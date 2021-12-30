package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.entity.*;
import hr.fer.bookexchangeservice.service.ReviewService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/review")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("advert/{id}")
    public Review addAdvertReview(@PathVariable("id") @Parameter(name = "advertId") Advert advert,
                                  @RequestBody Review review) {
        return this.reviewService.addAdvertReview(review, advert);
    }

    @PostMapping("author/{id}")
    public Review addAuthorReview(@PathVariable("id") @Parameter(name = "authorId") Author author,
                                  @RequestBody Review review) {
        return this.reviewService.addAuthorReview(review, author);
    }

    @PostMapping("book/{id}")
    public Review addBookReview(@PathVariable("id") @Parameter(name = "bookId") Book book,
                                @RequestBody Review review) {
        return this.reviewService.addABookReview(review, book);
    }

    @PostMapping("user/{username}")
    public Review addUserReview(@PathVariable("username") @Parameter(name = "username") UserDetail user,
                                @RequestBody Review review) {
        return this.reviewService.addUserReview(review, user);
    }
}
