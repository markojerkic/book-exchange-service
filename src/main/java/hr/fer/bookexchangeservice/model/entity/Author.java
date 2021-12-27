package hr.fer.bookexchangeservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private String firstName;
    @Column
    @NotNull
    private String lastName;
    @Column
    @NotNull
    private Date yearOfBirth;
    @Column
    private Date yearOfDeath;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinTable(
            name = "author_reviews",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    @ToString.Exclude
    private List<Review> reviews;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinTable(
            name = "author_images",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    @ToString.Exclude
    private List<Image> authorImages;

    @ManyToMany
    @JoinTable(
            name = "author_wrote_genre",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @ToString.Exclude
    private List<Genre> authorsGenres;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Author author = (Author) o;
        return id != null && Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
