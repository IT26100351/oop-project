package com.shaahid.portal.owner;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// The "door" of the owner part. The frontend sends requests to these URLs.
//   GET    /api/owners        -> all owners
//   GET    /api/owners/O001   -> one owner
//   POST   /api/owners        -> create
//   PUT    /api/owners/O001   -> update
//   DELETE /api/owners/O001   -> delete
@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private OwnerManager manager = new OwnerManager();

    @GetMapping
    public List<Owner> getAll() {
        return manager.getAllOwners();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        Owner owner = manager.getOwnerById(id);
        if (owner == null) {
            return ResponseEntity.status(404).body("Owner not found");
        }
        return ResponseEntity.ok(owner);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Owner owner) {
        if (owner.getName() == null || owner.getName().isBlank() || owner.getPhone() <= 0) {
            return ResponseEntity.badRequest().body("Name and valid phone number are required");
        }
        return ResponseEntity.ok(manager.addOwner(owner));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Owner owner) {
        Owner updated = manager.updateOwner(id, owner);
        if (updated == null) {
            return ResponseEntity.status(404).body("Owner not found");
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (manager.deleteOwner(id)) {
            return ResponseEntity.ok("Owner deleted");
        }
        return ResponseEntity.status(404).body("Owner not found");
    }
}
