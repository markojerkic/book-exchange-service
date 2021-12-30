package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.model.entity.*;
import hr.fer.bookexchangeservice.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getAuthorReviews(Author author) {
        return this.reviewRepository.findAllByAuthor(author);
    }

    /**
     * Returns float average of reviews. If no reviews, returns default -1.
     * @param author Instance of author by which query is run.
     * @return  Float average. Default -1 if no reviews.
     */
    public float getAverageAuthorReview(Author author) {
        return this.reviewRepository.averageReviewByAuthor(author);
    }

    /**
     * Returns float average of reviews. If no reviews, returns default -1.
     * @param book Instance of Book by which query is run
     * @return Float average. Default -1 if no reviews.
     */
    public float getAverageBookReview(Book book) {
        return this.reviewRepository.averageReviewByBook(book);
    }

    /**
     * Returns float average of reviews. If no reviews, returns default -1
     * @param advert Instance of Advert by which query is run.
     * @return loat average. Default -1 if no reviews.
     */
    public float getAverageAdvertReview(Advert advert) {
        return this.reviewRepository.averageReviewByAdvert(advert);
    }

    /**
     * Returns float average of reviews. If no reviews, returns default -1
     * @param user Instance of User by which query is run.
     * @return loat average. Default -1 if no reviews.
     */
    public float getAverageUserReview(UserDetail user) {
        return this.reviewRepository.averageReviewByUser(user);
    }

    public Review addAdvertReview(Review review, Advert advert) {
        review.setAdvert(advert);
        return this.addReview(review);
    }

    public Review addAuthorReview(Review review, Author author) {
        review.setAuthor(author);
        return this.addReview(review);
    }

    public Review addABookReview(Review review, Book book) {
        review.setBook(book);
        return this.addReview(review);
    }

    public Review addUserReview(Review review, UserDetail user) {
        review.setUser(user);
        return this.addReview(review);
    }

    private Review addReview(Review review) {
        return this.reviewRepository.save(review);
    }
}
