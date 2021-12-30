package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.entity.*;
import hr.fer.bookexchangeservice.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/review")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("advert/{id}")
    public Review addAdvertReview(@PathVariable("id") Advert advert,
                                  @RequestBody Review review) {
        review.setLastModified(new Date());
        return this.reviewService.addAdvertReview(review, advert);
    }

    @PostMapping("author/{id}")
    public Review addAuthorReview(@PathVariable("id") Author author,
                                  @RequestBody Review review) {
        review.setLastModified(new Date());
        return this.reviewService.addAuthorReview(review, author);
    }

    @PostMapping("book/{id}")
    public Review addBookReview(@PathVariable("id") Book book,
                                @RequestBody Review review) {
        review.setLastModified(new Date());
        return this.reviewService.addABookReview(review, book);
    }

    @PostMapping("user/{id}")
    public Review getUserReviews(@PathVariable("id") UserDetail user,
                                 @RequestBody Review review) {
        review.setLastModified(new Date());
        return this.reviewService.addUserReview(review, user);
    }

    @GetMapping("advert/{id}")
    public List<Review> getAdvertReviews(@PathVariable("id") Advert advert) {
        return this.reviewService.getAdvertReviews(advert);
    }

    @GetMapping("author/{id}")
    public List<Review> getAuthorReviews(@PathVariable("id") Author author) {
        return this.reviewService.getAuthorReviews(author);
    }

    @GetMapping("book/{id}")
    public List<Review> getBookReviews(@PathVariable("id") Book book) {
        return this.reviewService.getBookReviews(book);
    }

    @GetMapping("user/{username}")
    public List<Review> getUserReviews(@PathVariable String username) {
        return this.reviewService.getUserReviews(username);
    }
}
