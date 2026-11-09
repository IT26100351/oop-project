package com.shaahid.portal.apartment;

import com.shaahid.portal.common.Property;

/**
 * ============================================================================
 * OOP CONCEPT: INHERITANCE, POLYMORPHISM & ENCAPSULATION
 * Group Member: Hishadi (Member #3)
 * Package: com.shaahid.portal.apartment
 * Data Store: data/apartments.txt
 * ============================================================================
 * 
 * 1. INHERITANCE:
 *    - Extends the abstract Property class ("Apartment IS-A Property").
 *    - Reuses common fields: id, userId, size, sizeUnit, price, listingType, imageName, address, owner.
 *    - Specializes with apartment-specific attributes: 'rooms' (room count) and 'floor' (floor level).
 * 
 * 2. POLYMORPHISM:
 *    - Overrides getType() to return "apartment".
 *    - Overrides getDescription() using super.getDescription() to output apartment-specific text.
 *    - Overrides toFileString() to append apartment fields (|rooms|floor) to the parent's record.
 * 
 * 3. ENCAPSULATION:
 *    - 'rooms' and 'floor' fields are private, controlled via getters and setters.
 */
public class Apartment extends Property {

    // Number of bedrooms/rooms in the apartment
    private int rooms;

    // Floor number within the residential building (e.g., 5 for 5th floor)
    private int floor;

    // Default constructor needed for JSON deserialization by Spring Boot
    public Apartment() {
    }

    // Parameterized constructor
    public Apartment(int rooms, int floor) {
        this.rooms = rooms;
        this.floor = floor;
    }

    /**
     * Identifies this concrete Property type.
     */
    @Override
    public String getType() {
        return "apartment";
    }

    /**
     * POLYMORPHISM: Overrides description to display room count and floor level.
     */
    @Override
    public String getDescription() {
        return rooms + " room apartment on floor " + floor + ", " + super.getDescription();
    }

    /**
     * Serializes this Apartment into a pipe-delimited line for data/apartments.txt.
     * Format: <Property fields>|rooms|floor
     */
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + rooms + "|" + floor;
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
