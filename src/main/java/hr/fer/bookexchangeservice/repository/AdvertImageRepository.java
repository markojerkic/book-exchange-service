package hr.fer.bookexchangeservice.repository;

import hr.fer.bookexchangeservice.model.entity.image.AdvertImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AdvertImageRepository extends JpaRepository<AdvertImage, UUID> {
    List<AdvertImage> findAllByAdvert_Id(Long id);
    void deleteAllByAdvert_Id(Long id);
}
