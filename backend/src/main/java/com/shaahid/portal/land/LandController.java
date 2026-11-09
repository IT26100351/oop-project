package com.shaahid.portal.land;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ============================================================================
 * REST CONTROLLER: LAND API
 * Group Member: Senuka (Member #4)
 * Package: com.shaahid.portal.land
 * Base URL: /api/lands
 * ============================================================================
 * 
 * Endpoints:
 *   GET    /api/lands        -> Returns all lands
 *   GET    /api/lands/{id}   -> Returns one land by ID
 *   POST   /api/lands        -> Creates a new land listing
 *   PUT    /api/lands/{id}   -> Updates an existing land listing
 *   DELETE /api/lands/{id}   -> Deletes a land listing
 */
@RestController
@RequestMapping("/api/lands")
public class LandController {

    private LandManager manager = new LandManager();

    /**
     * Retrieves all land listings.
     */
    @GetMapping
    public List<Land> getAll() {
        return manager.getAllLands();
    }

    /**
     * Retrieves a single land listing by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        Land land = manager.getLandById(id);
        if (land == null) {
            return ResponseEntity.status(404).body("Land not found");
        }
        return ResponseEntity.ok(land);
    }

    /**
     * Creates a new land listing.
     * Validates contact phone, location, size, and price.
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Land land) {
        if (land.getOwner() == null || land.getOwner().getPhone() <= 0) {
            return ResponseEntity.badRequest().body("A contact phone number is required");
        }
        if (land.getAddress() == null || land.getAddress().getLocation() == null || land.getAddress().getLocation().isBlank()) {
            return ResponseEntity.badRequest().body("Location is required");
        }
        if (land.getPrice() <= 0 || land.getSize() <= 0) {
            return ResponseEntity.badRequest().body("Size and price must be more than 0");
        }
        return ResponseEntity.ok(manager.addLand(land));
    }

    /**
     * Updates an existing land listing.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Land land) {
        Land updated = manager.updateLand(id, land);
        if (updated == null) {
            return ResponseEntity.status(404).body("Land not found");
        }
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a land listing.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (manager.deleteLand(id)) {
            return ResponseEntity.ok("Land deleted");
        }
        return ResponseEntity.status(404).body("Land not found");
    }
}
