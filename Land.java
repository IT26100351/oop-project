package com.shaahid.portal.land;

import com.shaahid.portal.common.Property;

// A Land IS-A Property (INHERITANCE). It adds nothing new, because land
// has no rooms - but it is still its own class with its own type and description.
public class Land extends Property {

    public Land() {
    }

    @Override
    public String getType() {
        return "land";
    }

    // POLYMORPHISM: land describes itself differently from a house or apartment
    @Override
    public String getDescription() {
        return "Land, " + super.getDescription();
    }

    // No extra fields, so the file line is the same as the parent's line.
    // (We don't need to override toFileString() here.)
}
