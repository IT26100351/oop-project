package com.shaahid.portal.house;

import com.shaahid.portal.common.Property;

/**
 * ============================================================================
 * OOP CONCEPT: INHERITANCE, POLYMORPHISM & ENCAPSULATION
 * Group Member: Amaya (Member #2)
 * Package: com.shaahid.portal.house
 * Data Store: data/houses.txt
 * ============================================================================
 * 
 * 1. INHERITANCE:
 *    - Extends the abstract Property class ("House IS-A Property").
 *    - Reuses common fields: id, userId, size, sizeUnit, price, listingType, imageName, address, owner.
 *    - Specializes with house-specific attributes: 'rooms' (number of bedrooms/rooms).
 * 
 * 2. POLYMORPHISM:
 *    - Overrides getType() to return "house".
 *    - Overrides getDescription() using super.getDescription() to create a custom house description.
 *    - Overrides toFileString() to append house-specific fields to the parent's serialized record.
 * 
 * 3. ENCAPSULATION:
 *    - 'rooms' is private with public getters and setters.
 */
public class House extends Property {

    // Number of rooms in the house
    private int rooms;

    // Default constructor needed for JSON deserialization by Spring Boot
    public House() {
    }

    // Parameterized constructor
    public House(int rooms) {
        this.rooms = rooms;
    }

    /**
     * Identifies this concrete Property type.
     */
    @Override
    public String getType() {
        return "house";
    }

    /**
     * POLYMORPHISM: Overrides the base description to include room count.
     * Calls super.getDescription() to build upon the parent's logic.
     */
    @Override
    public String getDescription() {
        return rooms + " room house, " + super.getDescription();
    }

    /**
     * Serializes this House into a pipe-delimited line for data/houses.txt.
     * Format: <Property fields>|rooms
     */
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + rooms;
    }

    // =========================================================================
    // ENCAPSULATION: Getters and Setters
    // =========================================================================

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }
}
