package com.shaahid.portal.land;

import com.shaahid.portal.common.FileHelper;
import com.shaahid.portal.owner.Owner;
import com.shaahid.portal.owner.OwnerManager;
import java.util.ArrayList;
import java.util.List;

// All the CRUD work for lands. Data file: data/lands.txt
public class LandManager {

    private static final String FILE = "data/lands.txt";

    // We need the OwnerManager to save and find owners (AGGREGATION in action)
    private OwnerManager ownerManager = new OwnerManager();

    // Turn one text line into a Land object
    private Land lineToLand(String line) {
        String[] parts = line.split("\\|", -1);        // cut the line at every "|"
        Land land = new Land();
        land.fillFromParts(parts);                    // the parent fills the shared fields
        land.setOwner(ownerManager.getOwnerById(parts[9]));   // find the owner by id
        return land;
    }

    // Write the whole list back into the file
    private void saveAll(List<Land> lands) {
        List<String> lines = new ArrayList<>();
        for (Land land : lands) {
            lines.add(land.toFileString());
        }
        FileHelper.writeLines(FILE, lines);
    }

    // READ ALL
    public List<Land> getAllLands() {
        List<Land> lands = new ArrayList<>();
        for (String line : FileHelper.readLines(FILE)) {
            lands.add(lineToLand(line));
        }
        return lands;
    }

    // READ ONE (returns null when not found)
    public Land getLandById(String id) {
        for (Land land : getAllLands()) {
            if (land.getId().equals(id)) {
                return land;
            }
        }
        return null;
    }

    // CREATE
    public Land addLand(Land land) {
        // 1) save the owner first, in its own file (it gets an id like O003)
        Owner savedOwner = ownerManager.addOwner(land.getOwner());
        land.setOwner(savedOwner);
        // 2) give the land a new id and add its line to the file
        List<String> lines = FileHelper.readLines(FILE);
        land.setId(FileHelper.nextId("L", lines));
        lines.add(land.toFileString());
        FileHelper.writeLines(FILE, lines);
        return land;
    }

    // UPDATE (returns null when not found)
    public Land updateLand(String id, Land newData) {
        List<Land> lands = getAllLands();
        for (int i = 0; i < lands.size(); i++) {
            Land old = lands.get(i);
            if (old.getId().equals(id)) {
                newData.setId(id);                    // keep the same id
                newData.setUserId(old.getUserId());   // keep who created it
                newData.setOwner(old.getOwner());     // keep the same owner
                lands.set(i, newData);               // replace old with new
                saveAll(lands);
                return newData;
            }
        }
        return null;
    }

    // DELETE (returns true if something was deleted). The owner stays in owners.txt.
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
