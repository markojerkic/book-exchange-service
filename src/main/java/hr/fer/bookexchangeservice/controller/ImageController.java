package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.entity.Image;
import hr.fer.bookexchangeservice.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/image")
@CrossOrigin("*")
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    public List<Image> saveImages(@RequestParam("images") MultipartFile[] images) {
        return this.imageService.saveImages(images);
    }

    @GetMapping(value = "/{imageUuid}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getFile(@PathVariable UUID imageUuid) {
        Image image = this.imageService.getImageByUuid(imageUuid);

        HttpHeaders headers = new HttpHeaders();
        switch (image.getImageFileExtension()) {
            case JPEG:
            case JPG:
                headers.setContentType(MediaType.IMAGE_JPEG);
                break;
            case PNG:
                headers.setContentType(MediaType.IMAGE_PNG);
                break;
            default:
                throw new IllegalArgumentException();
        }

        byte[] imageFile = this.imageService.getImageFile(image);

        return new ResponseEntity<byte[]>(imageFile, headers, HttpStatus.OK);
    }
}
