package hr.fer.bookexchangeservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import hr.fer.bookexchangeservice.model.constant.AdvertStatus;
import hr.fer.bookexchangeservice.model.constant.AdvertType;
import hr.fer.bookexchangeservice.model.constant.TransactionType;
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
    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private UserDetail userCreated;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Book advertisedBook;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(orphanRemoval = true, mappedBy = "advert")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Image> advertImages;

}
