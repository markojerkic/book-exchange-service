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
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @NotNull
    private String name;
    @Column
    private String description;
    @ManyToMany(mappedBy = "authorsGenres")
    private List<Author> authors;
    @ManyToMany(mappedBy = "genres")
    private List<Book> books;
}
