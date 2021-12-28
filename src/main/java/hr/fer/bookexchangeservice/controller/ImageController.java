package hr.fer.bookexchangeservice.controller;

import hr.fer.bookexchangeservice.model.entity.Image;
import hr.fer.bookexchangeservice.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
}
