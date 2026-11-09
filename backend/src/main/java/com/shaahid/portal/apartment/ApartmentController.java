package com.shaahid.portal.apartment;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ============================================================================
 * REST CONTROLLER: APARTMENT API
 * Group Member: Hishadi (Member #3)
 * Package: com.shaahid.portal.apartment
 * Base URL: /api/apartments
 * ============================================================================
 * 
 * Endpoints:
 *   GET    /api/apartments        -> Returns all apartments
 *   GET    /api/apartments/{id}   -> Returns one apartment by ID
 *   POST   /api/apartments        -> Creates a new apartment listing
 *   PUT    /api/apartments/{id}   -> Updates an existing apartment listing
 *   DELETE /api/apartments/{id}   -> Deletes an apartment listing
 */
@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {

    private ApartmentManager manager = new ApartmentManager();

    /**
     * Retrieves all apartment listings.
     */
    @GetMapping
    public List<Apartment> getAll() {
        return manager.getAllApartments();
    }

    /**
     * Retrieves a single apartment listing by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        Apartment apartment = manager.getApartmentById(id);
        if (apartment == null) {
            return ResponseEntity.status(404).body("Apartment not found");
        }
        return ResponseEntity.ok(apartment);
    }

    /**
     * Creates a new apartment listing.
     * Validates contact phone, location, size, and price.
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Apartment apartment) {
        if (apartment.getOwner() == null || apartment.getOwner().getPhone() <= 0) {
            return ResponseEntity.badRequest().body("A contact phone number is required");
        }
        if (apartment.getAddress() == null || apartment.getAddress().getLocation() == null || apartment.getAddress().getLocation().isBlank()) {
            return ResponseEntity.badRequest().body("Location is required");
        }
        if (apartment.getPrice() <= 0 || apartment.getSize() <= 0) {
            return ResponseEntity.badRequest().body("Size and price must be more than 0");
        }
        return ResponseEntity.ok(manager.addApartment(apartment));
    }

    /**
     * Updates an existing apartment listing.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Apartment apartment) {
        Apartment updated = manager.updateApartment(id, apartment);
        if (updated == null) {
            return ResponseEntity.status(404).body("Apartment not found");
        }
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes an apartment listing.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (manager.deleteApartment(id)) {
            return ResponseEntity.ok("Apartment deleted");
        }
        return ResponseEntity.status(404).body("Apartment not found");
    }
}
