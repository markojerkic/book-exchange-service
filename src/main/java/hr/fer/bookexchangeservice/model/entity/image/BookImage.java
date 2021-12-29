package hr.fer.bookexchangeservice.model.entity.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.fer.bookexchangeservice.model.entity.Book;
import hr.fer.bookexchangeservice.model.entity.Image;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class BookImage extends Image {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Book book;
}
