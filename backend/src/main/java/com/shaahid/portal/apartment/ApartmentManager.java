package com.shaahid.portal.apartment;

import com.shaahid.portal.common.FileHelper;
import com.shaahid.portal.owner.Owner;
import com.shaahid.portal.owner.OwnerManager;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * BUSINESS LOGIC & DATA ACCESS: APARTMENT MANAGEMENT
 * Group Member: Hishadi (Member #3)
 * Package: com.shaahid.portal.apartment
 * Data Store: data/apartments.txt
 * ============================================================================
 * 
 * Implements CRUD operations for Apartment listings:
 * 1. CREATE (addApartment): Auto-generates ID ("A001") and persists owner via OwnerManager.
 * 2. READ (getAllApartments, getApartmentById): Parses apartment records.
 * 3. UPDATE (updateApartment): Modifies details while retaining creator and owner references.
 * 4. DELETE (deleteApartment): Removes the record from apartments.txt.
 * 
 * OOP AGGREGATION:
 * - Aggregates Owner via OwnerManager (the Owner entity survives if apartment is deleted).
 */
public class ApartmentManager {

    private static final String FILE = "data/apartments.txt";

    // OwnerManager dependency for resolving owner aggregations
    private OwnerManager ownerManager = new OwnerManager();

    /**
     * Converts a single line from data/apartments.txt into an Apartment object.
     * Tokens: 0..9 = Property fields, 10 = rooms, 11 = floor.
     */
    private Apartment lineToApartment(String line) {
        String[] parts = line.split("\\|", -1);
        Apartment apartment = new Apartment();
        apartment.fillFromParts(parts);
        if (parts.length > 10 && !parts[10].isBlank()) {
            try {
                apartment.setRooms(Integer.parseInt(parts[10].trim()));
            } catch (NumberFormatException e) {
                apartment.setRooms(0);
            }
        }
        if (parts.length > 11 && !parts[11].isBlank()) {
            try {
                apartment.setFloor(Integer.parseInt(parts[11].trim()));
            } catch (NumberFormatException e) {
                apartment.setFloor(0);
            }
        }
        if (parts.length > 9) {
            apartment.setOwner(ownerManager.getOwnerById(parts[9]));
        }
        return apartment;
    }

    /**
     * Rewrites data/apartments.txt with the updated list of apartments.
     */
    private void saveAll(List<Apartment> apartments) {
        List<String> lines = new ArrayList<>();
        for (Apartment apartment : apartments) {
            lines.add(apartment.toFileString());
        }
        FileHelper.writeLines(FILE, lines);
    }

    /**
     * READ ALL: Retrieves all apartment listings.
     */
    public List<Apartment> getAllApartments() {
        List<Apartment> apartments = new ArrayList<>();
        for (String line : FileHelper.readLines(FILE)) {
            apartments.add(lineToApartment(line));
        }
        return apartments;
    }

    /**
     * READ ONE: Retrieves an apartment listing by ID (e.g., "A001").
     */
    public Apartment getApartmentById(String id) {
        for (Apartment apartment : getAllApartments()) {
            if (apartment.getId().equals(id)) {
                return apartment;
            }
        }
        return null;
    }

    /**
     * CREATE: Saves owner first (if new), assigns a new Apartment ID, and appends to apartments.txt.
     */
    public Apartment addApartment(Apartment apartment) {
        // 1. Save owner via OwnerManager (Aggregation)
        Owner savedOwner = ownerManager.addOwner(apartment.getOwner());
        apartment.setOwner(savedOwner);

        // 2. Assign unique ID and persist
        List<String> lines = FileHelper.readLines(FILE);
        apartment.setId(FileHelper.nextId("A", lines));
        lines.add(apartment.toFileString());
        FileHelper.writeLines(FILE, lines);
        return apartment;
    }

    /**
     * UPDATE: Updates apartment fields while preserving ID and creator ID.
     */
    public Apartment updateApartment(String id, Apartment newData) {
        List<Apartment> apartments = getAllApartments();
        for (int i = 0; i < apartments.size(); i++) {
            Apartment old = apartments.get(i);
            if (old.getId().equals(id)) {
                newData.setId(id);
                newData.setUserId(old.getUserId());
                if (newData.getOwner() == null) {
                    newData.setOwner(old.getOwner());
                }
                apartments.set(i, newData);
                saveAll(apartments);
                return newData;
            }
        }
        return null;
    }

    /**
     * DELETE: Deletes an apartment listing from apartments.txt.
     */
    public boolean deleteApartment(String id) {
        List<Apartment> apartments = getAllApartments();
        for (int i = 0; i < apartments.size(); i++) {
            if (apartments.get(i).getId().equals(id)) {
                apartments.remove(i);
                saveAll(apartments);
                return true;
            }
        }
        return false;
    }
}
