package hr.fer.bookexchangeservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OneToMany(mappedBy = "author")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
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
    @ToString.Exclude
    private List<Genre> authorsGenres;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookAuthor")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<Book> books;

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
