package com.shaahid.portal.image;

import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// Handles property photos.
//   POST   /api/images          -> upload a photo, returns the saved file name
//   DELETE /api/images/<name>   -> delete a photo
// Photos are saved in data/images/ and can be viewed at http://localhost:8080/images/<name>
@RestController
@RequestMapping("/api/images")
public class ImageController {

    private static final String FOLDER = "data/images";

    private static File getFolder() {
        File folder = new File(FOLDER);
        if (!folder.exists() && new File("backend/" + FOLDER).exists()) {
            return new File("backend/" + FOLDER);
        }
        if (!folder.exists() && new File("../" + FOLDER).exists()) {
            return new File("../" + FOLDER);
        }
        return folder;
    }

    @PostMapping
    public ResponseEntity<?> upload(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "files", required = false) MultipartFile[] files) {
        File folder = getFolder();
        folder.mkdirs();

        // Handle multiple files if sent
        if (files != null && files.length > 0) {
            java.util.List<String> names = new java.util.ArrayList<>();
            for (MultipartFile f : files) {
                if (f != null && !f.isEmpty()) {
                    String original = f.getOriginalFilename();
                    if (original == null) {
                        original = "photo.jpg";
                    }
                    String safeName = System.currentTimeMillis() + "_" + ((int) (Math.random() * 1000)) + "_" + original.replaceAll("[^a-zA-Z0-9._-]", "_");
                    try {
                        f.transferTo(new File(folder, safeName).getAbsoluteFile());
                        names.add(safeName);
                    } catch (IOException e) {
                        return ResponseEntity.status(500).body("Could not save the image: " + original);
                    }
                }
            }
            if (!names.isEmpty()) {
                return ResponseEntity.ok(names);
            }
        }

        // Handle single file
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file chosen");
        }
        String type = file.getContentType();
        if (type == null || !type.startsWith("image/")) {
            return ResponseEntity.badRequest().body("Please choose an image file");
        }
        // make a safe, unique file name: time + original name without strange characters
        String original = file.getOriginalFilename();
        if (original == null) {
            original = "photo.jpg";
        }
        String safeName = System.currentTimeMillis() + "_" + original.replaceAll("[^a-zA-Z0-9._-]", "_");
        try {
            file.transferTo(new File(folder, safeName).getAbsoluteFile());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Could not save the image");
        }
        return ResponseEntity.ok(safeName);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        File file = new File(getFolder(), name.replaceAll("[^a-zA-Z0-9._-]", "_"));
        if (file.exists() && file.delete()) {
            return ResponseEntity.ok("Image deleted");
        }
        return ResponseEntity.status(404).body("Image not found");
    }
}
