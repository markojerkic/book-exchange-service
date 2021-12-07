package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.model.entity.Advert;
import hr.fer.bookexchangeservice.repository.AdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdvertService {
    private final AdvertRepository advertRepository;

    public List<Advert> getAllAdverts() {
        return this.advertRepository.findAll();
    }

    public Advert saveAdvert(Advert advert) {
        return this.advertRepository.save(advert);
    }
}
