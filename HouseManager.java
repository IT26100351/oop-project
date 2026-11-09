package com.shaahid.portal.house;

import com.shaahid.portal.common.FileHelper;
import com.shaahid.portal.owner.Owner;
import com.shaahid.portal.owner.OwnerManager;
import java.util.ArrayList;
import java.util.List;

// All the CRUD work for houses. Data file: data/houses.txt
public class HouseManager {

    private static final String FILE = "data/houses.txt";

    // We need the OwnerManager to save and find owners (AGGREGATION in action)
    private OwnerManager ownerManager = new OwnerManager();

    // Turn one text line into a House object
    private House lineToHouse(String line) {
        String[] parts = line.split("\\|", -1);        // cut the line at every "|"
        House house = new House();
        house.fillFromParts(parts);                    // the parent fills the shared fields
        house.setRooms(Integer.parseInt(parts[10]));   // ROOMS
        house.setOwner(ownerManager.getOwnerById(parts[9]));   // find the owner by id
        return house;
    }

    // Write the whole list back into the file
    private void saveAll(List<House> houses) {
        List<String> lines = new ArrayList<>();
        for (House house : houses) {
            lines.add(house.toFileString());
        }
        FileHelper.writeLines(FILE, lines);
    }

    // READ ALL
    public List<House> getAllHouses() {
        List<House> houses = new ArrayList<>();
        for (String line : FileHelper.readLines(FILE)) {
            houses.add(lineToHouse(line));
        }
        return houses;
    }

    // READ ONE (returns null when not found)
    public House getHouseById(String id) {
        for (House house : getAllHouses()) {
            if (house.getId().equals(id)) {
                return house;
            }
        }
        return null;
    }

    // CREATE
    public House addHouse(House house) {
        // 1) save the owner first, in its own file (it gets an id like O003)
        Owner savedOwner = ownerManager.addOwner(house.getOwner());
        house.setOwner(savedOwner);
        // 2) give the house a new id and add its line to the file
        List<String> lines = FileHelper.readLines(FILE);
        house.setId(FileHelper.nextId("H", lines));
        lines.add(house.toFileString());
        FileHelper.writeLines(FILE, lines);
        return house;
    }

    // UPDATE (returns null when not found)
    public House updateHouse(String id, House newData) {
        List<House> houses = getAllHouses();
        for (int i = 0; i < houses.size(); i++) {
            House old = houses.get(i);
            if (old.getId().equals(id)) {
                newData.setId(id);                    // keep the same id
                newData.setUserId(old.getUserId());   // keep who created it
                newData.setOwner(old.getOwner());     // keep the same owner
                houses.set(i, newData);               // replace old with new
                saveAll(houses);
                return newData;
            }
        }
        return null;
    }

    // DELETE (returns true if something was deleted). The owner stays in owners.txt.
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
