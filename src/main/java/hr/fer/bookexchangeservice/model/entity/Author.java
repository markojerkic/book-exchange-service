package hr.fer.bookexchangeservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "author")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Review> reviews;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "author")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Image> authorImages;
    @ManyToMany
    @JoinTable(
            name = "author_wrote_genre",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> authorsGenres;
    @JsonIgnore
    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bookAuthor")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Book> books;
}
