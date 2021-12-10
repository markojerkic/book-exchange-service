package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.entity.Advert;
import hr.fer.bookexchangeservice.service.AdvertService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/advert")
public class AdvertController {
    private final AdvertService advertService;

    @GetMapping("all")
    public List<Advert> getAllAdverts() {
        return this.advertService.getAllAdverts();
    }

    @PostMapping
    public Advert saveAdvert(@RequestBody Advert advert) {
        return this.advertService.saveAdvert(advert);
    }

    @GetMapping
    public Page<Advert> getAdvertPage(Pageable pageable) {
        return this.advertService.getAdvertPage(pageable);
    }
}
