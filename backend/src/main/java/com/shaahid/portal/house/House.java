package com.shaahid.portal.house;

import com.shaahid.portal.common.Property;

// A House IS-A Property (INHERITANCE: "extends Property").
// It gets id, size, price, address, owner ... from Property for free
// and only adds what is special about a house: the number of rooms.
public class House extends Property {

    private int rooms;   // number of rooms

    // Empty constructor (needed so Spring can build a House from JSON)
    public House() {
    }

    // Constructor with the extra field
    public House(int rooms) {
        this.rooms = rooms;
    }

    // Every child must say what type it is
    @Override
    public String getType() {
        return "house";
    }

    // POLYMORPHISM: we OVERRIDE getDescription() to give a house-style sentence.
    // "super.getDescription()" calls the parent's version and we add to it.
    @Override
    public String getDescription() {
        return rooms + " room house, " + super.getDescription();
    }

    // A house line in the file = the parent's line + "|rooms"
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + rooms;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }
}
