package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.constant.AdvertType;
import hr.fer.bookexchangeservice.model.constant.TransactionType;
import hr.fer.bookexchangeservice.model.entity.Advert;
import hr.fer.bookexchangeservice.service.AdvertService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/advert")
@CrossOrigin("*")
public class AdvertController {
    private final AdvertService advertService;

    @GetMapping("all")
    public List<Advert> getAllAdverts() {
        return this.advertService.getAllAdverts();
    }

    @GetMapping("{id}")
    public Advert getAdvertById(@PathVariable Long id) {
        return this.advertService.getAdvertById(id);
    }

    @PatchMapping("{id}")
    public Advert updateAdvert(@PathVariable Long id, @RequestBody Advert advert) {
        return this.advertService.updateById(id, advert);
    }

    @PostMapping
    public Advert saveAdvert(@RequestBody Advert advert) {
        return this.advertService.saveAdvert(advert);
    }

    @GetMapping
    public Page<Advert> getAdvertPage(Pageable pageable, @RequestParam Optional<Long> authorId,
                                      @RequestParam Optional<Long> genreId,
                                      @RequestParam Optional<Long> bookId,
                                      @RequestParam Optional<AdvertType> advertType,
                                      @RequestParam Optional<TransactionType> transactionType,
                                      @RequestParam Optional<Long> fromPrice,
                                      @RequestParam Optional<Long> toPrice,
                                      @RequestParam Optional<String> isbn,
                                      @RequestParam Optional<String> query) {

        return this.advertService.getAdvertPage(pageable, authorId, genreId, bookId, advertType, transactionType,
                fromPrice, toPrice, isbn, query);
    }
}
