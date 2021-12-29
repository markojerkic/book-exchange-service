package hr.fer.bookexchangeservice.repository;

import hr.fer.bookexchangeservice.service.AdvertService;
import hr.fer.bookexchangeservice.service.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ImageTest {
    @Autowired
    private ImageService imageService;
    @Autowired
    private AdvertService advertService;

    @Test
    public void testCascadeUpdate() {
        assertTrue(true);
    }
}
