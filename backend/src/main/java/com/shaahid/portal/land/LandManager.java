package com.shaahid.portal.land;

import com.shaahid.portal.common.FileHelper;
import com.shaahid.portal.owner.Owner;
import com.shaahid.portal.owner.OwnerManager;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * BUSINESS LOGIC & DATA ACCESS: LAND MANAGEMENT
 * Group Member: Senuka (Member #4)
 * Package: com.shaahid.portal.land
 * Data Store: data/lands.txt
 * ============================================================================
 * 
 * Implements CRUD operations for Land listings:
 * 1. CREATE (addLand): Auto-generates ID ("L001") and persists owner via OwnerManager.
 * 2. READ (getAllLands, getLandById): Parses land records from lands.txt.
 * 3. UPDATE (updateLand): Modifies details while retaining creator and owner references.
 * 4. DELETE (deleteLand): Removes the record from lands.txt.
 * 
 * OOP AGGREGATION:
 * - Aggregates Owner via OwnerManager (the Owner survives even if the land listing is deleted).
 */
public class LandManager {

    private static final String FILE = "data/lands.txt";

    // OwnerManager dependency for resolving owner aggregations
    private OwnerManager ownerManager = new OwnerManager();

    /**
     * Converts a single line from data/lands.txt into a Land object.
     */
    private Land lineToLand(String line) {
        String[] parts = line.split("\\|", -1);
        Land land = new Land();
        land.fillFromParts(parts);
        if (parts.length > 9) {
            land.setOwner(ownerManager.getOwnerById(parts[9]));
        }
        return land;
    }

    /**
     * Rewrites data/lands.txt with the updated list of lands.
     */
    private void saveAll(List<Land> lands) {
        List<String> lines = new ArrayList<>();
        for (Land land : lands) {
            lines.add(land.toFileString());
        }
        FileHelper.writeLines(FILE, lines);
    }

    /**
     * READ ALL: Retrieves all land listings.
     */
    public List<Land> getAllLands() {
        List<Land> lands = new ArrayList<>();
        for (String line : FileHelper.readLines(FILE)) {
            lands.add(lineToLand(line));
        }
        return lands;
    }

    /**
     * READ ONE: Retrieves a single land listing by ID (e.g., "L001").
     */
    public Land getLandById(String id) {
        for (Land land : getAllLands()) {
            if (land.getId().equals(id)) {
                return land;
            }
        }
        return null;
    }

    /**
     * CREATE: Saves owner first, assigns a new Land ID, and appends to lands.txt.
     */
    public Land addLand(Land land) {
        // 1. Save owner via OwnerManager (Aggregation)
        Owner savedOwner = ownerManager.addOwner(land.getOwner());
        land.setOwner(savedOwner);

        // 2. Assign unique ID and persist
        List<String> lines = FileHelper.readLines(FILE);
        land.setId(FileHelper.nextId("L", lines));
        lines.add(land.toFileString());
        FileHelper.writeLines(FILE, lines);
        return land;
    }

    /**
     * UPDATE: Updates land fields while preserving ID and creator ID.
     */
    public Land updateLand(String id, Land newData) {
        List<Land> lands = getAllLands();
        for (int i = 0; i < lands.size(); i++) {
            Land old = lands.get(i);
            if (old.getId().equals(id)) {
                newData.setId(id);
                newData.setUserId(old.getUserId());
                if (newData.getOwner() == null) {
                    newData.setOwner(old.getOwner());
                }
                lands.set(i, newData);
                saveAll(lands);
                return newData;
            }
        }
        return null;
    }

    /**
     * DELETE: Deletes a land listing from lands.txt.
     */
    public boolean deleteLand(String id) {
        List<Land> lands = getAllLands();
        for (int i = 0; i < lands.size(); i++) {
            if (lands.get(i).getId().equals(id)) {
                lands.remove(i);
                saveAll(lands);
                return true;
            }
        }
        return false;
    }
}
