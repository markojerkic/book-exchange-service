package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.exception.AdvertNotFoundException;
import hr.fer.bookexchangeservice.model.constant.AdvertStatus;
import hr.fer.bookexchangeservice.model.constant.AdvertType;
import hr.fer.bookexchangeservice.model.constant.TransactionType;
import hr.fer.bookexchangeservice.model.entity.Advert;
import hr.fer.bookexchangeservice.model.entity.UserDetail;
import hr.fer.bookexchangeservice.repository.AdvertRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Page<Advert> getAdvertPage(Pageable pageable, Optional<Long> authorId, Optional<Long> genreId,
                                      Optional<Long> bookId, Optional<AdvertType> advertType,
                                      Optional<TransactionType> transactionType, Optional<Long> fromPrice,
                                      Optional<Long> toPrice, Optional<String> isbn, Optional<String> query) {

        return this.advertRepository.findAll(this.createQuerySpecification(authorId, genreId, bookId,
                advertType, transactionType, fromPrice, toPrice, isbn, query), pageable);
    }

    private Specification<Advert> createQuerySpecification(Optional<Long> authorId, Optional<Long> genreId,
                                                           Optional<Long> bookId, Optional<AdvertType> advertType,
                                                           Optional<TransactionType> transactionType,
                                                           Optional<Long> fromPrice,
                                                           Optional<Long> toPrice, Optional<String> isbn,
                                                           Optional<String> queryString) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            authorId.ifPresent(aLong -> predicates.add(criteriaBuilder.equal(root.get("advertisedBook").get("bookAuthor").get("id"), aLong)));
            bookId.ifPresent(aLong -> predicates.add(criteriaBuilder.equal(root.get("advertisedBook").get("id"),
                    aLong)));
            genreId.ifPresent(aLong -> predicates.add(criteriaBuilder.equal(root.join("advertisedBook").join("genres").join("id"),
                    aLong)));
            advertType.ifPresent(type -> predicates.add(criteriaBuilder.equal(root.get("advertType"),
                    type)));
            transactionType.ifPresent(type -> predicates.add(criteriaBuilder.equal(root.get("transactionType"),
                    type)));
            isbn.ifPresent(s -> predicates.add(criteriaBuilder.equal(root.get("advertisedBook").get("ISBN"),
                    s)));
            fromPrice.ifPresent(aLong -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"),
                    aLong)));
            toPrice.ifPresent(aLong -> predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"),
                    aLong)));
            queryString.ifPresent(s -> predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("title"),
                            "%" + s + "%"),
                    criteriaBuilder.like(root.get("description"),
                            "%" + s + "%"))));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Advert getAdvertById(Long id) {
        return this.advertRepository.findById(id).orElseThrow(() -> new AdvertNotFoundException("Oglas " + id + " nije pronađen"));
    }

    @Secured("ROLE_ADMIN")
    public Advert updateById(Long id, Advert advert) {
        if (!this.advertRepository.existsById(id)) {
            throw new AdvertNotFoundException("Oglas " + id + " nije pronađen");
        }
        advert.setId(id);
        return this.advertRepository.save(advert);
    }
}
