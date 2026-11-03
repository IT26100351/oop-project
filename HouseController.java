package com.shaahid.portal.house;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// URLs for the house part:
//   GET    /api/houses        -> all houses
//   GET    /api/houses/H001   -> one house
//   POST   /api/houses        -> create a house
//   PUT    /api/houses/H001   -> update
//   DELETE /api/houses/H001   -> delete
@RestController
@RequestMapping("/api/houses")
public class HouseController {

    private HouseManager manager = new HouseManager();

    @GetMapping
    public List<House> getAll() {
        return manager.getAllHouses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        House house = manager.getHouseById(id);
        if (house == null) {
            return ResponseEntity.status(404).body("House not found");
        }
        return ResponseEntity.ok(house);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody House house) {
        // simple checks before saving
        if (house.getOwner() == null || house.getOwner().getPhone() <= 0) {
            return ResponseEntity.badRequest().body("A contact phone number is required");
        }
        if (house.getAddress().getLocation() == null || house.getAddress().getLocation().isBlank()) {
            return ResponseEntity.badRequest().body("Location is required");
        }
        if (house.getPrice() <= 0 || house.getSize() <= 0) {
            return ResponseEntity.badRequest().body("Size and price must be more than 0");
        }
        return ResponseEntity.ok(manager.addHouse(house));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody House house) {
        House updated = manager.updateHouse(id, house);
        if (updated == null) {
            return ResponseEntity.status(404).body("House not found");
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (manager.deleteHouse(id)) {
            return ResponseEntity.ok("House deleted");
        }
        return ResponseEntity.status(404).body("House not found");
    }
}
