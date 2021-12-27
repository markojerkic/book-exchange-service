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
    @OneToMany(mappedBy = "author")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Review> reviews;
    @OneToMany(mappedBy = "author")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Image> authorImages;
    @ManyToMany()
    @JoinTable(
            name = "author_wrote_genre",
            joinColumns = @JoinColumn(name = "author_id",
                    foreignKey = @ForeignKey(name = "genre_author_id_fk",
                            value = ConstraintMode.CONSTRAINT,
                            foreignKeyDefinition = "foreign key (author_id) references author\n" +
                                    "on delete cascade")),
            inverseJoinColumns = @JoinColumn(name = "genre_id",
                    foreignKey = @ForeignKey(name = "author_genre_id_fk",
                            value = ConstraintMode.CONSTRAINT,
                            foreignKeyDefinition = "foreign key (genre_id) references genre\n" +
                                    "on delete cascade"))
    )
    private List<Genre> authorsGenres;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookAuthor")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Book> books;
}
