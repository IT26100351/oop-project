package com.shaahid.portal.land;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// URLs for the land part:
//   GET    /api/lands        -> all lands
//   GET    /api/lands/L001   -> one land
//   POST   /api/lands        -> create a land
//   PUT    /api/lands/L001   -> update
//   DELETE /api/lands/L001   -> delete
@RestController
@RequestMapping("/api/lands")
public class LandController {

    private LandManager manager = new LandManager();

    @GetMapping
    public List<Land> getAll() {
        return manager.getAllLands();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        Land land = manager.getLandById(id);
        if (land == null) {
            return ResponseEntity.status(404).body("Land not found");
        }
        return ResponseEntity.ok(land);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Land land) {
        // simple checks before saving
        if (land.getOwner() == null || land.getOwner().getPhone() <= 0) {
            return ResponseEntity.badRequest().body("A contact phone number is required");
        }
        if (land.getAddress().getLocation() == null || land.getAddress().getLocation().isBlank()) {
            return ResponseEntity.badRequest().body("Location is required");
        }
        if (land.getPrice() <= 0 || land.getSize() <= 0) {
            return ResponseEntity.badRequest().body("Size and price must be more than 0");
        }
        return ResponseEntity.ok(manager.addLand(land));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Land land) {
        Land updated = manager.updateLand(id, land);
        if (updated == null) {
            return ResponseEntity.status(404).body("Land not found");
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (manager.deleteLand(id)) {
            return ResponseEntity.ok("Land deleted");
        }
        return ResponseEntity.status(404).body("Land not found");
    }
}
