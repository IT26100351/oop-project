package com.shaahid.portal.house;

import com.shaahid.portal.common.FileHelper;
import com.shaahid.portal.owner.Owner;
import com.shaahid.portal.owner.OwnerManager;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * BUSINESS LOGIC & DATA ACCESS: HOUSE MANAGEMENT
 * Group Member: Amaya (Member #2)
 * Package: com.shaahid.portal.house
 * Data Store: data/houses.txt
 * ============================================================================
 * 
 * Implements CRUD operations for House listings:
 * 1. CREATE (addHouse): Generates unique House ID ("H001") and persists associated Owner via OwnerManager.
 * 2. READ (getAllHouses, getHouseById): Reads and maps pipe-delimited records into House objects.
 * 3. UPDATE (updateHouse): Modifies existing house attributes while preserving owner and creator IDs.
 * 4. DELETE (deleteHouse): Removes the house from houses.txt (Owner remains in owners.txt - Aggregation).
 * 
 * OOP AGGREGATION:
 * - Uses OwnerManager to resolve and persist the owner associated with each house.
 */
public class HouseManager {

    private static final String FILE = "data/houses.txt";

    // OwnerManager dependency for resolving owner aggregations
    private OwnerManager ownerManager = new OwnerManager();

    /**
     * Converts a single line from data/houses.txt into a House object.
     * Tokens: 0..9 are common Property fields; 10 is rooms.
     */
    private House lineToHouse(String line) {
        String[] parts = line.split("\\|", -1);
        House house = new House();
        house.fillFromParts(parts);
        if (parts.length > 10 && !parts[10].isBlank()) {
            try {
                house.setRooms(Integer.parseInt(parts[10].trim()));
            } catch (NumberFormatException e) {
                house.setRooms(0);
            }
        }
        // parts[9] is the owner ID; resolve the full Owner object
        if (parts.length > 9) {
            house.setOwner(ownerManager.getOwnerById(parts[9]));
        }
        return house;
    }

    /**
     * Rewrites data/houses.txt with the updated list of houses.
     */
    private void saveAll(List<House> houses) {
        List<String> lines = new ArrayList<>();
        for (House house : houses) {
            lines.add(house.toFileString());
        }
        FileHelper.writeLines(FILE, lines);
    }

    /**
     * READ ALL: Returns all registered house listings.
     */
    public List<House> getAllHouses() {
        List<House> houses = new ArrayList<>();
        for (String line : FileHelper.readLines(FILE)) {
            houses.add(lineToHouse(line));
        }
        return houses;
    }

    /**
     * READ ONE: Finds a house by its ID (e.g., "H001").
     */
    public House getHouseById(String id) {
        for (House house : getAllHouses()) {
            if (house.getId().equals(id)) {
                return house;
            }
        }
        return null;
    }

    /**
     * CREATE: Saves owner first (if new), assigns a new House ID, and appends to houses.txt.
     */
    public House addHouse(House house) {
        // 1. Persist owner entity first (Aggregation)
        Owner savedOwner = ownerManager.addOwner(house.getOwner());
        house.setOwner(savedOwner);

        // 2. Assign next unique House ID and write to file
        List<String> lines = FileHelper.readLines(FILE);
        house.setId(FileHelper.nextId("H", lines));
        lines.add(house.toFileString());
        FileHelper.writeLines(FILE, lines);
        return house;
    }

    /**
     * UPDATE: Updates house attributes while preserving the original ID and creator user ID.
     */
    public House updateHouse(String id, House newData) {
        List<House> houses = getAllHouses();
        for (int i = 0; i < houses.size(); i++) {
            House old = houses.get(i);
            if (old.getId().equals(id)) {
                newData.setId(id);
                newData.setUserId(old.getUserId());
                if (newData.getOwner() == null) {
                    newData.setOwner(old.getOwner());
                }
                houses.set(i, newData);
                saveAll(houses);
                return newData;
            }
        }
        return null;
    }

    /**
     * DELETE: Removes a house listing from data/houses.txt.
     */
    public boolean deleteHouse(String id) {
        List<House> houses = getAllHouses();
        for (int i = 0; i < houses.size(); i++) {
            if (houses.get(i).getId().equals(id)) {
                houses.remove(i);
                saveAll(houses);
                return true;
            }
        }
        return false;
    }
}
