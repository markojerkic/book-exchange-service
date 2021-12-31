package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.constant.ReviewType;
import hr.fer.bookexchangeservice.model.dto.ReviewDTO;
import hr.fer.bookexchangeservice.model.entity.*;
import hr.fer.bookexchangeservice.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("api/review")
@AllArgsConstructor
@CrossOrigin("*")
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

    @GetMapping
    public ReviewDTO getReviews(@SortDefault(value = "lastModified", direction = Sort.Direction.DESC) Pageable pageable,
                                @RequestParam ReviewType reviewType,
                                @RequestParam Optional<Long> bookId,
                                @RequestParam Optional<Long> authorId,
                                @RequestParam Optional<Long> advertId,
                                @RequestParam Optional<String> username) {
        return this.reviewService.getReviews(pageable, reviewType, bookId, authorId, advertId, username);
    }
}
