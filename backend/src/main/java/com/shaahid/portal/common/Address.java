package com.shaahid.portal.common;

/**
 * ============================================================================
 * OOP CONCEPT: COMPOSITION & ENCAPSULATION
 * Group: SE1020 Group WD04
 * Component: Shared Common Address Component
 * ============================================================================
 * 
 * COMPOSITION:
 * - An Address is a vital sub-component of a Property.
 * - An address cannot meaningfully exist without a property to which it belongs.
 * - Created directly by the Property class instance.
 * 
 * ENCAPSULATION:
 * - Attributes 'location' and 'street' are declared private.
 * - Access is strictly controlled through public getters and setters.
 */
public class Address {

    // Town, city, or district (e.g., "Colombo 07", "Kandy", "Mount Lavinia")
    private String location;

    // Detailed street address (e.g., "15 Gregory's Road", "42 Chapel Road")
    private String street;

    // Default constructor needed for Spring Boot JSON deserialization
    public Address() {
    }

    // Parameterized constructor for initialization
    public Address(String location, String street) {
        this.location = location;
        this.street = street;
    }

    // =========================================================================
    // ENCAPSULATION: Getters and Setters
    // =========================================================================

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
