package com.shaahid.portal.house;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ============================================================================
 * REST CONTROLLER: HOUSE API
 * Group Member: Amaya (Member #2)
 * Package: com.shaahid.portal.house
 * Base URL: /api/houses
 * ============================================================================
 * 
 * Endpoints:
 *   GET    /api/houses        -> Returns all houses
 *   GET    /api/houses/{id}   -> Returns one house by ID
 *   POST   /api/houses        -> Creates a new house listing
 *   PUT    /api/houses/{id}   -> Updates an existing house listing
 *   DELETE /api/houses/{id}   -> Deletes a house listing
 */
@RestController
@RequestMapping("/api/houses")
public class HouseController {

    private HouseManager manager = new HouseManager();

    /**
     * Retrieves all house listings.
     */
    @GetMapping
    public List<House> getAll() {
        return manager.getAllHouses();
    }

    /**
     * Retrieves a single house listing by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        House house = manager.getHouseById(id);
        if (house == null) {
            return ResponseEntity.status(404).body("House not found");
        }
        return ResponseEntity.ok(house);
    }

    /**
     * Creates a new house listing.
     * Validates contact phone, location, size, and price.
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody House house) {
        if (house.getOwner() == null || house.getOwner().getPhone() <= 0) {
            return ResponseEntity.badRequest().body("A contact phone number is required");
        }
        if (house.getAddress() == null || house.getAddress().getLocation() == null || house.getAddress().getLocation().isBlank()) {
            return ResponseEntity.badRequest().body("Location is required");
        }
        if (house.getPrice() <= 0 || house.getSize() <= 0) {
            return ResponseEntity.badRequest().body("Size and price must be more than 0");
        }
        return ResponseEntity.ok(manager.addHouse(house));
    }

    /**
     * Updates an existing house listing.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody House house) {
        House updated = manager.updateHouse(id, house);
        if (updated == null) {
            return ResponseEntity.status(404).body("House not found");
        }
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a house listing.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (manager.deleteHouse(id)) {
            return ResponseEntity.ok("House deleted");
        }
        return ResponseEntity.status(404).body("House not found");
    }
}
