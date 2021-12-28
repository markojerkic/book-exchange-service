package hr.fer.bookexchangeservice.repository;

import hr.fer.bookexchangeservice.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}
