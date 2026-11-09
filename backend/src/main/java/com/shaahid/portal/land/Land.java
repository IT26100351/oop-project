package com.shaahid.portal.land;

import com.shaahid.portal.common.Property;

/**
 * ============================================================================
 * OOP CONCEPT: INHERITANCE, POLYMORPHISM & ENCAPSULATION
 * Group Member: Senuka (Member #4)
 * Package: com.shaahid.portal.land
 * Data Store: data/lands.txt
 * ============================================================================
 * 
 * 1. INHERITANCE:
 *    - Extends the abstract Property class ("Land IS-A Property").
 *    - Reuses common fields: id, userId, size, sizeUnit, price, listingType, imageName, address, owner.
 *    - Unlike House or Apartment, Land has no interior rooms, demonstrating how subclasses can represent simpler domains without unnecessary fields.
 * 
 * 2. POLYMORPHISM:
 *    - Overrides getType() to return "land".
 *    - Overrides getDescription() to output a land-specific prefix.
 *    - Uses parent's toFileString() without modification as no extra fields are added.
 */
public class Land extends Property {

    // Default constructor needed for JSON deserialization by Spring Boot
    public Land() {
    }

    /**
     * Identifies this concrete Property type.
     */
    @Override
    public String getType() {
        return "land";
    }

    /**
     * POLYMORPHISM: Overrides the description to clearly indicate a Land property.
     */
    @Override
    public String getDescription() {
        return "Land, " + super.getDescription();
    }
}
