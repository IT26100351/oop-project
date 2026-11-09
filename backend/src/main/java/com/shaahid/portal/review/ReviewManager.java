package com.shaahid.portal.review;

import com.shaahid.portal.common.FileHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * BUSINESS LOGIC & DATA ACCESS: REVIEW MANAGEMENT
 * Group Member: Thasreef (Member #5)
 * Package: com.shaahid.portal.review
 * Data Store: data/reviews.txt
 * ============================================================================
 * 
 * Implements CRUD operations for customer property reviews:
 * 1. CREATE (addReview): Assigns next ID ("R001") and persists review.
 * 2. READ (getAllReviews, getReviewsByProperty, getReviewById):
 *    - Allows filtering reviews by property ID for property details view.
 * 3. UPDATE (updateReview): Updates existing review comments and ratings.
 * 4. DELETE (deleteReview): Removes a review by ID.
 */
public class ReviewManager {

    private static final String FILE = "data/reviews.txt";

    /**
     * Converts a single text line from data/reviews.txt into a Review object.
     */
    private Review lineToReview(String line) {
        String[] parts = line.split("\\|", -1);
        int rating = 5;
        try {
            if (parts.length > 4 && !parts[4].isBlank()) {
                rating = Integer.parseInt(parts[4].trim());
            }
        } catch (NumberFormatException e) {
            rating = 5;
        }
        return new Review(
            parts[0], 
            parts[1], 
            parts[2], 
            parts[3], 
            rating, 
            parts.length > 5 ? parts[5] : ""
        );
    }

    /**
     * Rewrites data/reviews.txt with all current reviews.
     */
    private void saveAll(List<Review> reviews) {
        List<String> lines = new ArrayList<>();
        for (Review review : reviews) {
            lines.add(review.toFileString());
        }
        FileHelper.writeLines(FILE, lines);
    }

    /**
     * READ ALL: Retrieves all reviews in the system.
     */
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        for (String line : FileHelper.readLines(FILE)) {
            reviews.add(lineToReview(line));
        }
        return reviews;
    }

    /**
     * READ BY PROPERTY: Returns only reviews matching a specific property ID.
     * Used by details.html to display reviews for the currently viewed property.
     *
     * @param propertyId Target property ID (e.g., "H001", "A001").
     * @return Filtered list of Review objects.
     */
    public List<Review> getReviewsByProperty(String propertyId) {
        List<Review> result = new ArrayList<>();
        for (Review review : getAllReviews()) {
            if (review.getPropertyId().equals(propertyId)) {
                result.add(review);
            }
        }
        return result;
    }

    /**
     * READ ONE: Retrieves a review by its ID.
     */
    public Review getReviewById(String id) {
        for (Review review : getAllReviews()) {
            if (review.getId().equals(id)) {
                return review;
            }
        }
        return null;
    }

    /**
     * CREATE: Appends a new review with auto-generated ID (e.g., "R001").
     */
    public Review addReview(Review review) {
        List<String> lines = FileHelper.readLines(FILE);
        review.setId(FileHelper.nextId("R", lines));
        lines.add(review.toFileString());
        FileHelper.writeLines(FILE, lines);
        return review;
    }

    /**
     * UPDATE: Modifies review text and rating for an existing review.
     */
    public Review updateReview(String id, Review newData) {
        List<Review> reviews = getAllReviews();
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getId().equals(id)) {
                newData.setId(id);
                reviews.set(i, newData);
                saveAll(reviews);
                return newData;
            }
        }
        return null;
    }

    /**
     * DELETE: Removes a review by ID.
     */
    public boolean deleteReview(String id) {
        List<Review> reviews = getAllReviews();
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getId().equals(id)) {
                reviews.remove(i);
                saveAll(reviews);
                return true;
            }
        }
        return false;
    }
}
