package hr.fer.bookexchangeservice.model.entity.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import hr.fer.bookexchangeservice.model.entity.Advert;
import hr.fer.bookexchangeservice.model.entity.Image;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class AdvertImage extends Image {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advert_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Advert advert;
}
