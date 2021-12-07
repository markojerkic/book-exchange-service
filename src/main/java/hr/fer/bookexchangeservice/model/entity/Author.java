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
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @NotNull
    private String firstName;
    @Column
    @NotNull
    private String lastName;
    @Column
    @NotNull
    private int yearOfBirth;
    @Column
    private int yearOfDeath;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "author_reviews",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private List<Review> reviews;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "author_images",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> authorImages;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "auhthor_wrote_genre",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> authorsGenres;
}
