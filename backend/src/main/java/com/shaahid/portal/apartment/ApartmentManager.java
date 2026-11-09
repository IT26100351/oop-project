package com.shaahid.portal.apartment;

import com.shaahid.portal.common.FileHelper;
import com.shaahid.portal.owner.Owner;
import com.shaahid.portal.owner.OwnerManager;
import java.util.ArrayList;
import java.util.List;

// All the CRUD work for apartments. Data file: data/apartments.txt
public class ApartmentManager {

    private static final String FILE = "data/apartments.txt";

    // We need the OwnerManager to save and find owners (AGGREGATION in action)
    private OwnerManager ownerManager = new OwnerManager();

    // Turn one text line into a Apartment object
    private Apartment lineToApartment(String line) {
        String[] parts = line.split("\\|", -1);        // cut the line at every "|"
        Apartment apartment = new Apartment();
        apartment.fillFromParts(parts);                    // the parent fills the shared fields
        apartment.setRooms(Integer.parseInt(parts[10]));
        apartment.setFloor(Integer.parseInt(parts[11]));   // floor number
        apartment.setOwner(ownerManager.getOwnerById(parts[9]));   // find the owner by id
        return apartment;
    }

    // Write the whole list back into the file
    private void saveAll(List<Apartment> apartments) {
        List<String> lines = new ArrayList<>();
        for (Apartment apartment : apartments) {
            lines.add(apartment.toFileString());
        }
        FileHelper.writeLines(FILE, lines);
    }

    // READ ALL
    public List<Apartment> getAllApartments() {
        List<Apartment> apartments = new ArrayList<>();
        for (String line : FileHelper.readLines(FILE)) {
            apartments.add(lineToApartment(line));
        }
        return apartments;
    }

    // READ ONE (returns null when not found)
    public Apartment getApartmentById(String id) {
        for (Apartment apartment : getAllApartments()) {
            if (apartment.getId().equals(id)) {
                return apartment;
            }
        }
        return null;
    }

    // CREATE
    public Apartment addApartment(Apartment apartment) {
        // 1) save the owner first, in its own file (it gets an id like O003)
        Owner savedOwner = ownerManager.addOwner(apartment.getOwner());
        apartment.setOwner(savedOwner);
        // 2) give the apartment a new id and add its line to the file
        List<String> lines = FileHelper.readLines(FILE);
        apartment.setId(FileHelper.nextId("A", lines));
        lines.add(apartment.toFileString());
        FileHelper.writeLines(FILE, lines);
        return apartment;
    }

    // UPDATE (returns null when not found)
    public Apartment updateApartment(String id, Apartment newData) {
        List<Apartment> apartments = getAllApartments();
        for (int i = 0; i < apartments.size(); i++) {
            Apartment old = apartments.get(i);
            if (old.getId().equals(id)) {
                newData.setId(id);                    // keep the same id
                newData.setUserId(old.getUserId());   // keep who created it
                newData.setOwner(old.getOwner());     // keep the same owner
                apartments.set(i, newData);               // replace old with new
                saveAll(apartments);
                return newData;
            }
        }
        return null;
    }

    // DELETE (returns true if something was deleted). The owner stays in owners.txt.
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
