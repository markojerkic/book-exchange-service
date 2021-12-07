package hr.fer.bookexchangeservice.model.entity;

import com.sun.istack.NotNull;
import hr.fer.bookexchangeservice.model.constant.ImageFileExtension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID uuid;
    @Column
    @NotNull
    private ImageFileExtension imageFileExtension;
    @Column
    @NotNull
    private int imageOrder;
}
