package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.exception.AdvertNotFoundException;
import hr.fer.bookexchangeservice.model.constant.AdvertStatus;
import hr.fer.bookexchangeservice.model.constant.AdvertType;
import hr.fer.bookexchangeservice.model.constant.TransactionType;
import hr.fer.bookexchangeservice.model.entity.Advert;
import hr.fer.bookexchangeservice.model.entity.UserDetail;
import hr.fer.bookexchangeservice.repository.AdvertRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AdvertService {
    private final AdvertRepository advertRepository;
    private final AuthService authService;
    private final ImageService imageService;
    private final ReviewService reviewService;

    public List<Advert> getAllAdverts() {
        return this.advertRepository.findAll();
    }

    private Advert setAdvertInfo(Advert advert) {
        UserDetail userThatCreatedAdvert = this.authService.getCurrentUserDetail();
        advert.setUserCreated(userThatCreatedAdvert);
        advert.setAdvertStatus(AdvertStatus.ACTIVE);
        advert.setLastModified(new Date());
        return advert;
    }

    private void saveAdvertImages(Advert advert) {
        advert.getAdvertImages().stream().peek(image -> image.setAdvert(advert)).forEach(this.imageService::updateImage);
    }

    public Advert saveAdvert(Advert advert) {
        advert = this.setAdvertInfo(advert);
        Advert savedAdvert = this.advertRepository.save(advert);
        this.saveAdvertImages(savedAdvert);
        return savedAdvert;
    }

    public Advert updateById(Long id, Advert advert) {
        if (!this.advertRepository.existsById(id)) {
            throw new AdvertNotFoundException("Oglas " + id + " nije pronađen");
        }

        advert = this.setAdvertInfo(advert);
        advert.setId(id);
        Advert savedAdvert = this.advertRepository.save(advert);
        this.saveAdvertImages(advert);
        return savedAdvert;
    }

    public Page<Advert> getAdvertPage(Pageable pageable, Optional<Long> authorId, Optional<Long> genreId,
                                      Optional<Long> bookId, Optional<AdvertType> advertType,
                                      Optional<TransactionType> transactionType, Optional<Long> fromPrice,
                                      Optional<Long> toPrice, Optional<String> isbn, Optional<String> user,
                                      Optional<String> query) {

        return this.advertRepository.findAll(this.createQuerySpecification(authorId, genreId, bookId,
                advertType, transactionType, fromPrice, toPrice, isbn, user, query), pageable);
    }

    private Specification<Advert> createQuerySpecification(Optional<Long> authorId, Optional<Long> genreId,
                                                           Optional<Long> bookId, Optional<AdvertType> advertType,
                                                           Optional<TransactionType> transactionType,
                                                           Optional<Long> fromPrice,
                                                           Optional<Long> toPrice, Optional<String> isbn,
                                                           Optional<String> user,
                                                           Optional<String> queryString) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            authorId.ifPresent(aLong -> predicates.add(criteriaBuilder.equal(root.get("advertisedBook").get("bookAuthor")
                    .get("id"), aLong)));
            bookId.ifPresent(aLong -> predicates.add(criteriaBuilder.equal(root.get("advertisedBook").get("id"),
                    aLong)));
            genreId.ifPresent(aLong -> predicates.add(criteriaBuilder.equal(root.join("advertisedBook")
                            .join("genres").get("id"),
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
            user.ifPresent(username -> predicates.add(criteriaBuilder.equal(root.get("userCreated").get("username"),
                    username)));
            queryString.ifPresent(s -> predicates.add(criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder
                                    .upper(root.get("title")),
                            "%" + s.toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.upper(root.get("description")),
                            "%" + s.toUpperCase() + "%"))));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public Advert getAdvertById(Long id) {
        Advert advert = this.advertRepository.findById(id).orElseThrow(() ->
                new AdvertNotFoundException("Oglas " + id + " nije pronađen"));
        return advert;
    }

    public void deleteAdvertById(Long id) {
        Advert advert = this.getAdvertById(id);
        this.assertCurrentUserIsAuthor(advert);
        this.advertRepository.delete(advert);
        this.imageService.deleteImageFilesByAdvertId(id);
    }

    private void assertCurrentUserIsAuthor(Advert advert) {
        String currentUserUsername = this.authService.getCurrentUserDetail().getUsername();
        String userCreatedUsername = advert.getUserCreated().getUsername();
        if (!userCreatedUsername.equals(currentUserUsername)) {
            log.warn("User {} trying to edit/delete advert created by {}", currentUserUsername, userCreatedUsername);
            throw new AccessDeniedException("Forbidden");
        }
    }
}
