package com.shaahid.portal.image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * ============================================================================
 * REST CONTROLLER: PROPERTY IMAGE UPLOAD & MEDIA MANAGEMENT
 * Group Member: Dineth (Member #6)
 * Package: com.shaahid.portal.image
 * Base URL: /api/images
 * Storage Folder: data/images/
 * ============================================================================
 * 
 * Endpoints:
 *   POST   /api/images          -> Uploads single or multiple property photos
 *   DELETE /api/images/{name}   -> Deletes an uploaded photo file
 * 
 * Static Serving:
 *   Images stored in data/images/ are mapped and served by WebConfig at /images/{name}.
 */
@RestController
@RequestMapping("/api/images")
public class ImageController {

    private static final String FOLDER = "data/images";

    /**
     * Resolves the storage folder across runtime working directories:
     * - From root: "data/images"
     * - From backend: "backend/data/images" or "../data/images"
     */
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

    /**
     * Uploads single or multiple image files.
     * Sanitizes filenames and prepends a timestamp to guarantee unique names.
     *
     * @param file Optional single file parameter.
     * @param files Optional multi-file parameter array.
     * @return File name(s) stored on disk.
     */
    @PostMapping
    public ResponseEntity<?> upload(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "files", required = false) MultipartFile[] files) {
        File folder = getFolder();
        folder.mkdirs();

        // Handle multiple files
        if (files != null && files.length > 0) {
            List<String> names = new ArrayList<>();
            for (MultipartFile f : files) {
                if (f != null && !f.isEmpty()) {
                    String original = f.getOriginalFilename();
                    if (original == null) {
                        original = "photo.jpg";
                    }
                    String safeName = System.currentTimeMillis() + "_" 
                            + ((int) (Math.random() * 1000)) + "_" 
                            + original.replaceAll("[^a-zA-Z0-9._-]", "_");
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

        // Handle single file upload
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file chosen");
        }
        String type = file.getContentType();
        if (type == null || !type.startsWith("image/")) {
            return ResponseEntity.badRequest().body("Please choose a valid image file");
        }

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

    /**
     * Deletes an uploaded photo by filename.
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        File file = new File(getFolder(), name.replaceAll("[^a-zA-Z0-9._-]", "_"));
        if (file.exists() && file.delete()) {
            return ResponseEntity.ok("Image deleted");
        }
        return ResponseEntity.status(404).body("Image not found");
    }
}
