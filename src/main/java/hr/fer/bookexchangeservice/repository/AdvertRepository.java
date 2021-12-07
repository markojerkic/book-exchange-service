package hr.fer.bookexchangeservice.repository;

import hr.fer.bookexchangeservice.model.entity.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdvertRepository extends PagingAndSortingRepository<Advert, Long>, JpaRepository<Advert, Long> {
}
