package com.shaahid.portal.review;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// URLs for the review part:
//   GET    /api/reviews?propertyId=H001  -> reviews of one property (anyone can read)
//   GET    /api/reviews                  -> all reviews
//   POST   /api/reviews                  -> add a review
//   PUT    /api/reviews/R001             -> edit a review
//   DELETE /api/reviews/R001             -> delete a review
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private ReviewManager manager = new ReviewManager();

    // propertyId is optional: if it is given we filter, otherwise we return everything
    @GetMapping
    public List<Review> getAll(@RequestParam(required = false) String propertyId) {
        if (propertyId != null) {
            return manager.getReviewsByProperty(propertyId);
        }
        return manager.getAllReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        Review review = manager.getReviewById(id);
        if (review == null) {
            return ResponseEntity.status(404).body("Review not found");
        }
        return ResponseEntity.ok(review);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Review review) {
        if (review.getPropertyId() == null || review.getComment() == null || review.getComment().isBlank()) {
            return ResponseEntity.badRequest().body("A review needs a property and a comment");
        }
        if (review.getRating() < 1 || review.getRating() > 5) {
            return ResponseEntity.badRequest().body("Rating must be between 1 and 5");
        }
        return ResponseEntity.ok(manager.addReview(review));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Review review) {
        Review updated = manager.updateReview(id, review);
        if (updated == null) {
            return ResponseEntity.status(404).body("Review not found");
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (manager.deleteReview(id)) {
            return ResponseEntity.ok("Review deleted");
        }
        return ResponseEntity.status(404).body("Review not found");
    }
}
