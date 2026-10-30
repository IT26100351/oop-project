package com.shaahid.portal.review;

import com.shaahid.portal.common.FileHelper;

// A comment + star rating about ONE property. Saved in data/reviews.txt
public class Review {

    private String id;           // e.g. R001
    private String propertyId;   // which property this review is about, e.g. H001
    private String userId;       // who wrote it
    private String userName;     // shown next to the review
    private int rating;          // 1 to 5 stars
    private String comment;

    public Review() {
    }

    public Review(String id, String propertyId, String userId, String userName, int rating, String comment) {
        this.id = id;
        this.propertyId = propertyId;
        this.userId = userId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
    }

    // One line for the file: id|propertyId|userId|userName|rating|comment
    public String toFileString() {
        return id + "|" + FileHelper.clean(propertyId) + "|" + FileHelper.clean(userId) + "|"
                + FileHelper.clean(userName) + "|" + rating + "|" + FileHelper.clean(comment);
    }

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
