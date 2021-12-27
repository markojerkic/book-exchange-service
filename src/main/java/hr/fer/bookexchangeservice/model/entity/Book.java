package hr.fer.bookexchangeservice.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column
    @NotNull
    private String title;
    @Column(unique = true)
    @NotNull
    private String ISBN;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "book")
    @OnDelete(action = OnDeleteAction.CASCADE)
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
    private List<Genre> genres;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Image> bookImages;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_author_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Author bookAuthor;

}
