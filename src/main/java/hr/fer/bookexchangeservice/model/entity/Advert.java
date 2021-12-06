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
    @OneToOne
    @NotNull
    private UserDetail userCreated;
    @OneToOne
    @NotNull
    private Book advertisedBook;
    @OneToMany
    @JoinTable(name = "advert_images")
    private List<Image> advertImages;

}
