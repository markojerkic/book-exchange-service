package hr.fer.bookexchangeservice.model.entity;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    @NotNull
    private String title;
    @Column(unique = true)
    @NotNull
    private String ISBN;
    @OneToMany
    @JoinTable(name = "book_reviews")
    private List<Review> reviews;
    @ManyToMany
    @JoinTable(name = "book_is_in_genre")
    private List<Genre> genres;
    @OneToMany
    @JoinTable(name = "book_images")
    private List<Image> bookImages;

}
