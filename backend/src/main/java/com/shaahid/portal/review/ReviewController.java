package com.shaahid.portal.review;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ============================================================================
 * REST CONTROLLER: REVIEW API
 * Group Member: Thasreef (Member #5)
 * Package: com.shaahid.portal.review
 * Base URL: /api/reviews
 * ============================================================================
 * 
 * Endpoints:
 *   GET    /api/reviews?propertyId=H001  -> Returns reviews filtered for one property
 *   GET    /api/reviews                  -> Returns all reviews across all properties
 *   GET    /api/reviews/{id}             -> Returns one review by ID
 *   POST   /api/reviews                  -> Submits a new review
 *   PUT    /api/reviews/{id}             -> Updates an existing review
 *   DELETE /api/reviews/{id}             -> Deletes a review
 */
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private ReviewManager manager = new ReviewManager();

    /**
     * Retrieves reviews. Supports optional query parameter 'propertyId'
     * to filter reviews for a specific property (e.g., /api/reviews?propertyId=H001).
     */
    @GetMapping
    public List<Review> getAll(@RequestParam(required = false) String propertyId) {
        if (propertyId != null && !propertyId.isBlank()) {
            return manager.getReviewsByProperty(propertyId);
        }
        return manager.getAllReviews();
    }

    /**
     * Retrieves a single review by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        Review review = manager.getReviewById(id);
        if (review == null) {
            return ResponseEntity.status(404).body("Review not found");
        }
        return ResponseEntity.ok(review);
    }

    /**
     * Creates a new review.
     * Validates that target property ID and comment are provided, and that rating is 1..5.
     */
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

    /**
     * Updates an existing review.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Review review) {
        Review updated = manager.updateReview(id, review);
        if (updated == null) {
            return ResponseEntity.status(404).body("Review not found");
        }
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a review by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (manager.deleteReview(id)) {
            return ResponseEntity.ok("Review deleted");
        }
        return ResponseEntity.status(404).body("Review not found");
    }
}
