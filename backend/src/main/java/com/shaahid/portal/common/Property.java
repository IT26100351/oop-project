package com.shaahid.portal.common;

import com.shaahid.portal.owner.Owner;

/**
 * ============================================================================
 * OOP CONCEPT: ABSTRACTION & INHERITANCE (Parent Class)
 * Group: SE1020 Group WD04
 * Component: Shared Common Property Domain Model
 * ============================================================================
 * 
 * 1. ABSTRACTION:
 *    - Declared as 'abstract' because a generic "Property" cannot exist on its own in the real world.
 *    - Only concrete sub-types (House, Apartment, Land) can be instantiated.
 *    - Defines abstract method getType() which every sub-class MUST implement.
 * 
 * 2. INHERITANCE (Base Class):
 *    - Serves as the superclass for House, Apartment, and Land.
 *    - Shares common attributes (id, userId, size, sizeUnit, price, listingType, imageName, address, owner).
 *    - Eliminates redundant code across all property types.
 * 
 * 3. COMPOSITION:
 *    - 'address' is an instance of Address instantiated inside Property.
 *    - The lifecycle of Address is bound to Property; if Property is deleted, Address is deleted.
 * 
 * 4. AGGREGATION:
 *    - 'owner' is a reference to Owner.
 *    - The Owner exists independently in 'data/owners.txt'. If the Property is deleted, the Owner remains.
 * 
 * 5. ENCAPSULATION:
 *    - All fields are private, accessible only via public getters and setters.
 */
public abstract class Property {

    // Unique property identifier (e.g., H001, A001, L001)
    private String id;

    // ID of the user account who listed this property
    private String userId;

    // Size value (e.g., 2500, 15.5)
    private double size;

    // Measurement unit (e.g., "sqft" for house/apartment, "perches" for land)
    private String sizeUnit;

    // Price in Sri Lankan Rupees (LKR)
    private double price;

    // Listing intent: "Sale", "Rent", or "Lease"
    private String listingType;

    // Primary image filename stored in data/images/
    private String imageName;

    // COMPOSITION: Each property owns its Address component
    private Address address = new Address();

    // AGGREGATION: Property references an Owner (independent entity)
    private Owner owner;

    // Default constructor required for JSON deserialization by Spring Boot Jackson
    public Property() {
    }

    // Abstract method: Each subclass must specify its type ("house", "apartment", "land")
    public abstract String getType();

    /**
     * POLYMORPHISM: Base description generator.
     * Subclasses override this method to append subclass-specific details (e.g., rooms, floor).
     *
     * @return Formatted description of the property.
     */
    public String getDescription() {
        return size + " " + sizeUnit + " in " + (address != null ? address.getLocation() : "");
    }

    /**
     * Serializes this Property into a pipe-delimited string for file storage.
     * Format: id|userId|size|sizeUnit|price|listingType|imageName|location|street|ownerId
     *
     * @return Pipe-separated representation of the base fields.
     */
    public String toFileString() {
        String ownerId = (owner != null) ? owner.getId() : "";
        String loc = (address != null && address.getLocation() != null) ? address.getLocation() : "";
        String street = (address != null && address.getStreet() != null) ? address.getStreet() : "";

        return id + "|" 
                + FileHelper.clean(userId) + "|" 
                + size + "|" 
                + sizeUnit + "|" 
                + price + "|"
                + listingType + "|" 
                + FileHelper.clean(imageName) + "|"
                + FileHelper.clean(loc) + "|"
                + FileHelper.clean(street) + "|" 
                + ownerId;
    }

    /**
     * Populates common Property attributes by parsing tokens from a file line.
     *
     * @param parts Array of string tokens split by "|" from the data file.
     */
    public void fillFromParts(String[] parts) {
        this.id = parts[0];
        this.userId = parts[1];
        this.size = Double.parseDouble(parts[2]);
        this.sizeUnit = parts[3];
        this.price = Double.parseDouble(parts[4]);
        this.listingType = parts[5];
        this.imageName = parts[6];
        this.address = new Address(parts[7], parts[8]);
        // Note: parts[9] contains ownerId; resolved by respective Managers via OwnerManager
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getSizeUnit() {
        return sizeUnit;
    }

    public void setSizeUnit(String sizeUnit) {
        this.sizeUnit = sizeUnit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getListingType() {
        return listingType;
    }

    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
