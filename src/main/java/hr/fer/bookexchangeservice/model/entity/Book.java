package hr.fer.bookexchangeservice.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private String title;
    @Column(unique = true)
    @NotNull
    private String isbn;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "book")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<Review> reviews;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_is_in_genre",
            joinColumns = @JoinColumn(name = "book_id",
                    foreignKey = @ForeignKey(name = "book_genre_id_fk",
                            value = ConstraintMode.CONSTRAINT,
                    foreignKeyDefinition = "foreign key (book_id) references book\n" +
                            "on delete cascade")),
            inverseJoinColumns = @JoinColumn(name = "genre_id",
                    foreignKey = @ForeignKey(name = "genre_book_id_fk",
                            value = ConstraintMode.CONSTRAINT,
                            foreignKeyDefinition = "foreign key (genre_id) references genre\n" +
                                    "on delete cascade"))
    )
    @ToString.Exclude
    private List<Genre> genres;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "book")
    @ToString.Exclude
    private List<Image> bookImages;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_author_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Author bookAuthor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return id != null && Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
