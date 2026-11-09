package com.shaahid.portal.owner;

import com.shaahid.portal.common.FileHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * BUSINESS LOGIC & DATA ACCESS: OWNER MANAGEMENT
 * Group Member: Dineth (Member #6)
 * Package: com.shaahid.portal.owner
 * Data Store: data/owners.txt
 * ============================================================================
 * 
 * Implements CRUD operations for property Owners:
 * 1. CREATE (addOwner): Checks for existing owner by name & phone, or generates new ID ("O001").
 * 2. READ (getAllOwners, getOwnerById): Reads and maps records from owners.txt.
 * 3. UPDATE (updateOwner): Modifies owner details.
 * 4. DELETE (deleteOwner): Deletes an owner record.
 * 
 * Aggregation Support:
 * - Injected into HouseManager, ApartmentManager, and LandManager to manage owner entities.
 */
public class OwnerManager {

    private static final String FILE = "data/owners.txt";

    /**
     * Converts a single line from data/owners.txt into an Owner object.
     */
    private Owner lineToOwner(String line) {
        String[] parts = line.split("\\|", -1);
        int phone = 0;
        try {
            if (parts.length > 2 && !parts[2].isBlank()) {
                phone = Integer.parseInt(parts[2].trim());
            }
        } catch (NumberFormatException e) {
            phone = 0;
        }
        String userId = parts.length > 3 ? parts[3] : "";
        return new Owner(parts[0], parts[1], phone, userId);
    }

    /**
     * Rewrites data/owners.txt with the updated list of owners.
     */
    private void saveAll(List<Owner> owners) {
        List<String> lines = new ArrayList<>();
        for (Owner owner : owners) {
            lines.add(owner.toFileString());
        }
        FileHelper.writeLines(FILE, lines);
    }

    /**
     * READ ALL: Retrieves all owners.
     */
    public List<Owner> getAllOwners() {
        List<Owner> owners = new ArrayList<>();
        for (String line : FileHelper.readLines(FILE)) {
            owners.add(lineToOwner(line));
        }
        return owners;
    }

    /**
     * READ ONE: Retrieves an owner by ID (e.g., "O001").
     */
    public Owner getOwnerById(String id) {
        if (id == null || id.isBlank()) return null;
        for (Owner owner : getAllOwners()) {
            if (owner.getId().equalsIgnoreCase(id.trim())) {
                return owner;
            }
        }
        return null;
    }

    /**
     * CREATE: Saves a new owner or reuses existing owner if matching ID/phone exists.
     */
    public Owner addOwner(Owner owner) {
        if (owner == null) {
            return null;
        }

        // If owner already has an existing valid ID, verify it exists
        if (owner.getId() != null && !owner.getId().isBlank()) {
            Owner existing = getOwnerById(owner.getId());
            if (existing != null) {
                return existing;
            }
        }

        // Assign next auto-incremented owner ID (e.g., "O001")
        List<String> lines = FileHelper.readLines(FILE);
        owner.setId(FileHelper.nextId("O", lines));
        lines.add(owner.toFileString());
        FileHelper.writeLines(FILE, lines);
        return owner;
    }

    /**
     * UPDATE: Updates owner details by ID.
     */
    public Owner updateOwner(String id, Owner newData) {
        List<Owner> owners = getAllOwners();
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getId().equals(id)) {
                newData.setId(id);
                owners.set(i, newData);
                saveAll(owners);
                return newData;
            }
        }
        return null;
    }

    /**
     * DELETE: Removes an owner from data/owners.txt.
     */
    public boolean deleteOwner(String id) {
        List<Owner> owners = getAllOwners();
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getId().equals(id)) {
                owners.remove(i);
                saveAll(owners);
                return true;
            }
        }
        return false;
    }
}
