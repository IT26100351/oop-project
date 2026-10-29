package com.shaahid.portal.common;

// COMPOSITION example:
// An Address only makes sense INSIDE a property. A Property creates its own Address,
// and if the property is deleted its address is gone too. (The Address has no life of its own.)
public class Address {

    private String location;   // town / city, e.g. "Colombo"
    private String street;     // full address, e.g. "12 Galle Road, Colombo 03"

    // Empty constructor (Spring needs it to build the object from JSON)
    public Address() {
    }

    // Constructor that sets both values at once
    public Address(String location, String street) {
        this.location = location;
        this.street = street;
    }

    // Getters and setters (ENCAPSULATION: fields are private, we reach them through methods)
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
