package hr.fer.bookexchangeservice.service;

import hr.fer.bookexchangeservice.model.constant.ImageFileExtension;
import hr.fer.bookexchangeservice.model.entity.Image;
import hr.fer.bookexchangeservice.repository.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;

    private final Path root = Paths.get("images");

    public List<Image> saveImages(MultipartFile[] images) {
        return Arrays.stream(images).map(this::saveImageFile)
                .filter(image -> image.getUuid() != null)
                .collect(Collectors.toList());
    }

    private void createBaseDirIfNotExists() throws IOException {
        File dir = this.root.toFile();
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                throw new IOException();
            }
        }
    }

    private Image saveImageFile(MultipartFile image) {
        Image savedImage = new Image();
        try {
            String extension = Objects.requireNonNull(image.getOriginalFilename())
                    .substring(image.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase(Locale.ROOT);

            ImageFileExtension imageFileExtension;
            switch (extension) {
                case "png":
                    imageFileExtension = ImageFileExtension.PNG;
                    break;
                case "jpeg":
                    imageFileExtension = ImageFileExtension.JPEG;
                    break;
                case "jpg":
                    imageFileExtension = ImageFileExtension.JPG;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + extension);
            }
            savedImage.setImageFileExtension(imageFileExtension);
            savedImage = this.imageRepository.save(savedImage);

            this.createBaseDirIfNotExists();

            Files.copy(image.getInputStream(), this.root.resolve(savedImage.getImageFileName()));
            log.info("{} saved at {}", savedImage, this.root.resolve(savedImage.getImageFileName()));
        } catch (IOException e) {
            this.imageRepository.delete(savedImage);
            e.printStackTrace();
        }
        return savedImage;
    }
}
