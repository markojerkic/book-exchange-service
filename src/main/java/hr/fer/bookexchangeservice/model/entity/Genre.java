package hr.fer.bookexchangeservice.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private String name;
    @Column
    private String description;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(mappedBy = "authorsGenres",
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE)
    private List<Author> authors;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    @ManyToMany(mappedBy = "genres",
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE)
    private List<Book> books;
}
