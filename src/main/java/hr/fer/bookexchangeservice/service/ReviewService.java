package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.model.constant.ReviewType;
import hr.fer.bookexchangeservice.model.dto.ReviewDTO;
import hr.fer.bookexchangeservice.model.entity.*;
import hr.fer.bookexchangeservice.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;

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
     * @param username String unique username of user
     * @return loat average. Default -1 if no reviews.
     */
    public float getAverageUserReview(String username) {
        return this.reviewRepository.averageReviewByUser(username);
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

    public Page<Review> getAdvertReviews(Advert advert, Pageable pageable) {
        return this.reviewRepository.findAllByAdvertOrderByLastModifiedDesc(advert, pageable);
    }

    public Page<Review> getBookReviews(Book book, Pageable pageable) {
        return this.reviewRepository.findAllByBookOrderByLastModifiedDesc(book, pageable);
    }

    public Page<Review> getUserReviews(String username, Pageable pageable) {
        return this.reviewRepository.findAllByUser_UsernameOrderByLastModifiedDesc(username, pageable);
    }

    public Page<Review> getAuthorReviews(Author author, Pageable pageable) {
        return this.reviewRepository.findAllByAuthorOrderByLastModifiedDesc(author, pageable);
    }

    public ReviewDTO getReviews(Pageable pageable, 
                                ReviewType reviewType, 
                                Optional<Long> bookId, 
                                Optional<Long> authorId, 
                                Optional<Long> advertId,
                                Optional<String> username) {
        ReviewDTO reviews = new ReviewDTO();
        switch (reviewType) {
            case BOOK:
                reviews = this.setBookReviews(bookId, pageable);
                break;
            case AUTHOR:
                reviews = this.setAuthorReviews(authorId, pageable);
                break;
            case ADVERT:
                reviews = this.setAdvertReviews(advertId, pageable);
                break;
            case USER:
                reviews = this.setUserReviews(username, pageable);
                break;
        }
        return reviews;
    }

    private ReviewDTO setBookReviews(Optional<Long> bookId, Pageable pageable) {
        ReviewDTO reviews = new ReviewDTO();
        Book book = new Book();
        book.setId(bookId.orElseThrow(this::throwIllegalArgument));
        reviews.setReviews(this.getBookReviews(book, pageable));
        reviews.setAverageReviewGrade(this.getAverageBookReview(book));
        return reviews;
    }

    private ReviewDTO setAuthorReviews(Optional<Long> authorId, Pageable pageable) {
        ReviewDTO reviews = new ReviewDTO();
        Author author = new Author();
        author.setId(authorId.orElseThrow(this::throwIllegalArgument));
        reviews.setReviews(this.getAuthorReviews(author, pageable));
        reviews.setAverageReviewGrade(this.getAverageAuthorReview(author));
        return reviews;
    }

    private ReviewDTO setAdvertReviews(Optional<Long> id, Pageable pageable) {
        ReviewDTO reviews = new ReviewDTO();
        Advert advert = new Advert();
        advert.setId(id.orElseThrow(this::throwIllegalArgument));
        reviews.setReviews(this.getAdvertReviews(advert, pageable));
        reviews.setAverageReviewGrade(this.getAverageAdvertReview(advert));
        return reviews;
    }

    private ReviewDTO setUserReviews(Optional<String> usernameOpt, Pageable pageable) {
        ReviewDTO reviews = new ReviewDTO();
        String username = usernameOpt.orElseThrow(this::throwIllegalArgument);
        reviews.setReviews(this.getUserReviews(username, pageable));
        reviews.setAverageReviewGrade(this.getAverageUserReview(username));
        return reviews;
    }
    
    private IllegalArgumentException throwIllegalArgument() {
        return new IllegalArgumentException("Krivi argumenti");
    }
}
