package com.shaahid.portal.review;

import com.shaahid.portal.common.FileHelper;

/**
 * ============================================================================
 * OOP CONCEPT: ENCAPSULATION & DATA MODELING
 * Group Member: Thasreef (Member #5)
 * Package: com.shaahid.portal.review
 * Data Store: data/reviews.txt
 * ============================================================================
 * 
 * Represents a customer star rating (1-5) and feedback comment for any Property.
 * 
 * ENCAPSULATION:
 * - All attributes (id, propertyId, userId, userName, rating, comment) are private.
 * - Access is provided through public getters and setters.
 */
public class Review {

    // Unique review identifier (e.g., R001, R002)
    private String id;

    // ID of the target property (House, Apartment, or Land) being reviewed (e.g., H001, A001)
    private String propertyId;

    // ID of the user account who authored this review
    private String userId;

    // Display name of the reviewer
    private String userName;

    // Star rating score between 1 and 5
    private int rating;

    // Review comment / feedback text
    private String comment;

    // Default constructor needed for JSON deserialization by Spring Boot
    public Review() {
    }

    // Parameterized constructor
    public Review(String id, String propertyId, String userId, String userName, int rating, String comment) {
        this.id = id;
        this.propertyId = propertyId;
        this.userId = userId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
    }

    /**
     * Serializes this Review object into a pipe-delimited line for data/reviews.txt.
     * Format: id|propertyId|userId|userName|rating|comment
     */
    public String toFileString() {
        return id + "|" 
                + FileHelper.clean(propertyId) + "|" 
                + FileHelper.clean(userId) + "|"
                + FileHelper.clean(userName) + "|" 
                + rating + "|" 
                + FileHelper.clean(comment);
    }

    // =========================================================================
    // ENCAPSULATION: Getters and Setters
    // =========================================================================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
