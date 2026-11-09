package com.shaahid.portal.apartment;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// URLs for the apartment part:
//   GET    /api/apartments        -> all apartments
//   GET    /api/apartments/A001   -> one apartment
//   POST   /api/apartments        -> create an apartment
//   PUT    /api/apartments/A001   -> update
//   DELETE /api/apartments/A001   -> delete
@RestController
@RequestMapping("/api/apartments")
public class ApartmentController {

    private ApartmentManager manager = new ApartmentManager();

    @GetMapping
    public List<Apartment> getAll() {
        return manager.getAllApartments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        Apartment apartment = manager.getApartmentById(id);
        if (apartment == null) {
            return ResponseEntity.status(404).body("Apartment not found");
        }
        return ResponseEntity.ok(apartment);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Apartment apartment) {
        // simple checks before saving
        if (apartment.getOwner() == null || apartment.getOwner().getPhone() <= 0) {
            return ResponseEntity.badRequest().body("A contact phone number is required");
        }
        if (apartment.getAddress().getLocation() == null || apartment.getAddress().getLocation().isBlank()) {
            return ResponseEntity.badRequest().body("Location is required");
        }
        if (apartment.getPrice() <= 0 || apartment.getSize() <= 0) {
            return ResponseEntity.badRequest().body("Size and price must be more than 0");
        }
        return ResponseEntity.ok(manager.addApartment(apartment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Apartment apartment) {
        Apartment updated = manager.updateApartment(id, apartment);
        if (updated == null) {
            return ResponseEntity.status(404).body("Apartment not found");
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (manager.deleteApartment(id)) {
            return ResponseEntity.ok("Apartment deleted");
        }
        return ResponseEntity.status(404).body("Apartment not found");
    }
}
