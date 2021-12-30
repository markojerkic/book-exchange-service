package hr.fer.bookexchangeservice.repository;

import hr.fer.bookexchangeservice.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByAuthorOrderByLastModified(Author author);
    List<Review> findAllByBookOrderByLastModified(Book book);
    List<Review> findAllByAdvertOrderByLastModified(Advert advert);
    List<Review> findAllByUser_UsernameOrderByLastModified(String username);

    @Query("select coalesce(avg(r.reviewGrade), -1) from Review r where r.author = :author")
    Float averageReviewByAuthor(@Param("author") Author author);

    @Query("select coalesce(avg(r.reviewGrade), -1) from Review r where r.book = :book")
    Float averageReviewByBook(@Param("book") Book book);

    @Query("select coalesce(avg(r.reviewGrade), -1) from Review r where r.advert = :advert")
    Float averageReviewByAdvert(@Param("advert") Advert advert);

    @Query("select coalesce(avg(r.reviewGrade), -1) from Review r where r.user = :user")
    Float averageReviewByUser(@Param("user") UserDetail user);

}
