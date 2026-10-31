package com.shaahid.portal.apartment;

import com.shaahid.portal.common.Property;

// An Apartment IS-A Property (INHERITANCE). Extra things an apartment has:
// the number of rooms and the floor it is on.
public class Apartment extends Property {

    private int rooms;   // number of rooms
    private int floor;   // floor number, e.g. 5

    public Apartment() {
    }

    public Apartment(int rooms, int floor) {
        this.rooms = rooms;
        this.floor = floor;
    }

    @Override
    public String getType() {
        return "apartment";
    }

    // POLYMORPHISM: apartments describe themselves in their own way
    @Override
    public String getDescription() {
        return rooms + " room apartment on floor " + floor + ", " + super.getDescription();
    }

    // An apartment line in the file = the parent's line + "|rooms|floor"
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + rooms + "|" + floor;
    }

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
