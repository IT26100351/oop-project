package com.shaahid.portal.owner;

import com.shaahid.portal.common.FileHelper;
import java.util.ArrayList;
import java.util.List;

// All the CRUD work for owners. Data file: data/owners.txt
public class OwnerManager {

    private static final String FILE = "data/owners.txt";

    private Owner lineToOwner(String line) {
        String[] parts = line.split("\\|", -1);
        int phone = 0;
        try {
            if (parts.length > 2 && !parts[2].isBlank()) {
                phone = Integer.parseInt(parts[2].trim());
            }
        } catch (NumberFormatException e) {
            // keep 0 if parsing fails
        }
        String userId = parts.length > 3 ? parts[3] : "";
        return new Owner(parts[0], parts[1], phone, userId);
    }

    private void saveAll(List<Owner> owners) {
        List<String> lines = new ArrayList<>();
        for (Owner owner : owners) {
            lines.add(owner.toFileString());
        }
        FileHelper.writeLines(FILE, lines);
    }

    // READ ALL
    public List<Owner> getAllOwners() {
        List<Owner> owners = new ArrayList<>();
        for (String line : FileHelper.readLines(FILE)) {
            owners.add(lineToOwner(line));
        }
        return owners;
    }

    // READ ONE
    public Owner getOwnerById(String id) {
        for (Owner owner : getAllOwners()) {
            if (owner.getId().equals(id)) {
                return owner;
            }
        }
        return null;
    }

    // CREATE
    public Owner addOwner(Owner owner) {
        if (owner == null) {
            return null;
        }
        List<String> lines = FileHelper.readLines(FILE);
        owner.setId(FileHelper.nextId("O", lines));
        lines.add(owner.toFileString());
        FileHelper.writeLines(FILE, lines);
        return owner;
    }

    // UPDATE
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

    // DELETE
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
