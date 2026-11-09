package com.shaahid.portal.owner;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ============================================================================
 * REST CONTROLLER: OWNER API
 * Group Member: Dineth (Member #6)
 * Package: com.shaahid.portal.owner
 * Base URL: /api/owners
 * ============================================================================
 * 
 * Endpoints:
 *   GET    /api/owners        -> Returns all owners
 *   GET    /api/owners/{id}   -> Returns one owner by ID
 *   POST   /api/owners        -> Creates a new owner
 *   PUT    /api/owners/{id}   -> Updates an owner
 *   DELETE /api/owners/{id}   -> Deletes an owner
 */
@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private OwnerManager manager = new OwnerManager();

    /**
     * Retrieves all owners.
     */
    @GetMapping
    public List<Owner> getAll() {
        return manager.getAllOwners();
    }

    /**
     * Retrieves a single owner by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        Owner owner = manager.getOwnerById(id);
        if (owner == null) {
            return ResponseEntity.status(404).body("Owner not found");
        }
        return ResponseEntity.ok(owner);
    }

    /**
     * Creates a new owner.
     * Validates owner name and phone number.
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Owner owner) {
        if (owner.getName() == null || owner.getName().isBlank() || owner.getPhone() <= 0) {
            return ResponseEntity.badRequest().body("Name and valid phone number are required");
        }
        return ResponseEntity.ok(manager.addOwner(owner));
    }

    /**
     * Updates an existing owner.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Owner owner) {
        Owner updated = manager.updateOwner(id, owner);
        if (updated == null) {
            return ResponseEntity.status(404).body("Owner not found");
        }
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes an owner.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (manager.deleteOwner(id)) {
            return ResponseEntity.ok("Owner deleted");
        }
        return ResponseEntity.status(404).body("Owner not found");
    }
}
