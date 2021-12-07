package hr.fer.bookexchangeservice.model.entity;

import com.sun.istack.NotNull;
import hr.fer.bookexchangeservice.model.constant.AdvertStatus;
import hr.fer.bookexchangeservice.model.constant.AdvertType;
import hr.fer.bookexchangeservice.model.constant.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
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
    private AdvertType advertType;
    @Column
    @NotNull
    private TransactionType transactionType;
    @Column
    @NotNull
    private AdvertStatus advertStatus;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @NotNull
    private UserDetail userCreated;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @NotNull
    private Book advertisedBook;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "advert_images",
            joinColumns = @JoinColumn(name = "advert_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> advertImages;

}