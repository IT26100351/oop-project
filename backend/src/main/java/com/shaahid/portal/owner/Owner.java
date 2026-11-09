package com.shaahid.portal.owner;

import com.shaahid.portal.common.FileHelper;

/**
 * ============================================================================
 * OOP CONCEPT: AGGREGATION & ENCAPSULATION
 * Group Member: Dineth (Member #6)
 * Package: com.shaahid.portal.owner
 * Data Store: data/owners.txt
 * ============================================================================
 * 
 * Represents the owner or contact person for a property listing.
 * 
 * OOP AGGREGATION:
 * - An Owner exists independently of any Property.
 * - Stored in a separate file ('data/owners.txt').
 * - If a House, Apartment, or Land is deleted, the Owner entity continues to exist in the database.
 * - Multiple properties can reference the same Owner ID.
 * 
 * ENCAPSULATION:
 * - All attributes (id, name, phone, userId) are private.
 * - Access is exposed through public getters and setters.
 */
public class Owner {

    // Unique owner identifier (e.g., O001, O002)
    private String id;

    // Contact name of the owner/agent
    private String name;

    // Contact telephone number used by the frontend "Call to Inquire" button
    private int phone;

    // Creator user ID
    private String userId;

    // Default constructor needed for JSON deserialization by Spring Boot
    public Owner() {
    }

    // Parameterized constructor
    public Owner(String id, String name, int phone, String userId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.userId = userId;
    }

    /**
     * Serializes this Owner object into a pipe-delimited line for data/owners.txt.
     * Format: id|name|phone|userId
     */
    public String toFileString() {
        return id + "|" 
                + FileHelper.clean(name) + "|" 
                + phone + "|" 
                + FileHelper.clean(userId);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
