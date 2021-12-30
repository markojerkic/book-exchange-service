package hr.fer.bookexchangeservice.repository;

import hr.fer.bookexchangeservice.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    List<Image> findAllByAdvert_Id(Long id);
    List<Image> findAllByBook_Id(Long id);
    List<Image> findAllByAuthor_Id(Long id);
    void deleteAllByAdvert_Id(Long id);
    void deleteAllByBook_Id(Long id);
    void deleteAllByAuthor_Id(Long id);
}
