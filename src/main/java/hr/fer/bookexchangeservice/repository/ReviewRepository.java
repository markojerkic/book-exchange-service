package hr.fer.bookexchangeservice.repository;

import hr.fer.bookexchangeservice.model.entity.Advert;
import hr.fer.bookexchangeservice.model.entity.Author;
import hr.fer.bookexchangeservice.model.entity.Book;
import hr.fer.bookexchangeservice.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long>, PagingAndSortingRepository<Review, Long> {
    Page<Review> findAllByAuthorOrderByLastModifiedDesc(Author author, Pageable pageable);
    Page<Review> findAllByBookOrderByLastModifiedDesc(Book book, Pageable pageable);
    Page<Review> findAllByAdvertOrderByLastModifiedDesc(Advert advert, Pageable pageable);
    Page<Review> findAllByUser_UsernameOrderByLastModifiedDesc(String username, Pageable pageable);

    @Query("select coalesce(avg(r.reviewGrade), -1) from Review r where r.author = :author")
    Float averageReviewByAuthor(@Param("author") Author author);

    @Query("select coalesce(avg(r.reviewGrade), -1) from Review r where r.book = :book")
    Float averageReviewByBook(@Param("book") Book book);

    @Query("select coalesce(avg(r.reviewGrade), -1) from Review r where r.advert = :advert")
    Float averageReviewByAdvert(@Param("advert") Advert advert);

    @Query("select coalesce(avg(r.reviewGrade), -1) from Review r where r.user.username = :username")
    Float averageReviewByUser(@Param("username") String username);

}
