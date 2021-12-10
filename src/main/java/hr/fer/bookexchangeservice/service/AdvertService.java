package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.model.constant.AdvertStatus;
import hr.fer.bookexchangeservice.model.entity.Advert;
import hr.fer.bookexchangeservice.model.entity.UserDetail;
import hr.fer.bookexchangeservice.repository.AdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AdvertService {
    private final AdvertRepository advertRepository;
    private final AuthService authService;

    public List<Advert> getAllAdverts() {
        return this.advertRepository.findAll();
    }

    public Advert saveAdvert(Advert advert) {
        UserDetail userThatCreatedAdvert = this.authService.getCurrentUserDetail();
        advert.setUserCreated(userThatCreatedAdvert);
        advert.setAdvertStatus(AdvertStatus.ACTIVE);
        advert.setLastModified(new Date());

        return this.advertRepository.save(advert);
    }

    public Page<Advert> getAdvertPage(Pageable pageable) {
        return this.advertRepository.findAll(pageable);
    }
}
