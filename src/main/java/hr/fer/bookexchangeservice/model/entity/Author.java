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
    @OneToMany
    @JoinTable(name = "author_reviews")
    private List<Review> reviews;
    @OneToMany
    @JoinTable(name = "author_images")
    private List<Image> authorImages;
}
