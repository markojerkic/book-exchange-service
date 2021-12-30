package hr.fer.bookexchangeservice.model.dto;

import hr.fer.bookexchangeservice.model.entity.Review;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
public class ReviewDTO {
    private Page<Review> reviews;
    private Float averageReviewGrade;
}
