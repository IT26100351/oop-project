package com.shaahid.portal.common;

import com.shaahid.portal.owner.Owner;

// PARENT class (INHERITANCE): House, Apartment and Land all "extend" this class,
// so they reuse the fields and methods written here instead of copying them.
// "abstract" means you can never create a plain Property - only a House, Apartment or Land.
public abstract class Property {

    private String id;              // e.g. H001
    private String userId;          // which logged-in user created the listing
    private double size;            // the number, e.g. 2000
    private String sizeUnit;        // "sqft" or "perches"
    private double price;           // price in rupees
    private String listingType;     // "Rent", "Lease" or "Sale"
    private String imageName;       // photo file name (can be empty)

    // COMPOSITION: the property builds its own Address with "new". It cannot exist without us.
    private Address address = new Address();

    // AGGREGATION: the property HAS an owner, but the Owner exists on its own
    // (saved in its own file). Deleting the property does not delete the owner.
    private Owner owner;

    // Each child class tells us what kind of property it is.
    public abstract String getType();

    // A short sentence about the property. Children can OVERRIDE this (POLYMORPHISM).
    public String getDescription() {
        return size + " " + sizeUnit + " in " + address.getLocation();
    }

    // Turn this object into ONE line of text for the .txt file.
    // Order: id|userId|size|sizeUnit|price|listingType|imageName|location|street|ownerId
    public String toFileString() {
        String ownerId = "";
        if (owner != null) {
            ownerId = owner.getId();
        }
        return id + "|" + FileHelper.clean(userId) + "|" + size + "|" + sizeUnit + "|" + price + "|"
                + listingType + "|" + FileHelper.clean(imageName) + "|"
                + FileHelper.clean(address.getLocation()) + "|"
                + FileHelper.clean(address.getStreet()) + "|" + ownerId;
    }

    // The opposite: fill this object's fields from the parts of ONE line of the file.
    public void fillFromParts(String[] parts) {
        this.id = parts[0];
        this.userId = parts[1];
        this.size = Double.parseDouble(parts[2]);
        this.sizeUnit = parts[3];
        this.price = Double.parseDouble(parts[4]);
        this.listingType = parts[5];
        this.imageName = parts[6];
        this.address = new Address(parts[7], parts[8]);
        // parts[9] is the owner id - the manager finds the real Owner using it
    }

    // ----- Getters and setters -----
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
