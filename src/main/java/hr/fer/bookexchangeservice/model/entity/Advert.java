package hr.fer.bookexchangeservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import hr.fer.bookexchangeservice.model.constant.AdvertStatus;
import hr.fer.bookexchangeservice.model.constant.AdvertType;
import hr.fer.bookexchangeservice.model.constant.TransactionType;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private String title;
    @Column
    private String description;
    @Column
    @NotNull
    private Date lastModified;
    @Column
    private Float price;
    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private AdvertType advertType;
    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private AdvertStatus advertStatus;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    @NotNull
    @ToString.Exclude
    private UserDetail userCreated;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
    @NotNull
    @ToString.Exclude
    private Book advertisedBook;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval=true)
    @JoinTable(
            name = "advert_images",
            joinColumns = @JoinColumn(name = "advert_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    @ToString.Exclude
    private List<Image> advertImages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Advert advert = (Advert) o;
        return id != null && Objects.equals(id, advert.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
