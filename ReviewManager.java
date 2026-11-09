package com.shaahid.portal.review;

import com.shaahid.portal.common.FileHelper;
import java.util.ArrayList;
import java.util.List;

// All the CRUD work for reviews. Data file: data/reviews.txt
public class ReviewManager {

    private static final String FILE = "data/reviews.txt";

    private Review lineToReview(String line) {
        String[] parts = line.split("\\|", -1);
        return new Review(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), parts[5]);
    }

    private void saveAll(List<Review> reviews) {
        List<String> lines = new ArrayList<>();
        for (Review review : reviews) {
            lines.add(review.toFileString());
        }
        FileHelper.writeLines(FILE, lines);
    }

    // READ ALL
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        for (String line : FileHelper.readLines(FILE)) {
            reviews.add(lineToReview(line));
        }
        return reviews;
    }

    // READ: only the reviews of one property
    public List<Review> getReviewsByProperty(String propertyId) {
        List<Review> result = new ArrayList<>();
        for (Review review : getAllReviews()) {
            if (review.getPropertyId().equals(propertyId)) {
                result.add(review);
            }
        }
        return result;
    }

    // READ ONE
    public Review getReviewById(String id) {
        for (Review review : getAllReviews()) {
            if (review.getId().equals(id)) {
                return review;
            }
        }
        return null;
    }

    // CREATE
    public Review addReview(Review review) {
        List<String> lines = FileHelper.readLines(FILE);
        review.setId(FileHelper.nextId("R", lines));
        lines.add(review.toFileString());
        FileHelper.writeLines(FILE, lines);
        return review;
    }

    // UPDATE
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

    // DELETE
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
