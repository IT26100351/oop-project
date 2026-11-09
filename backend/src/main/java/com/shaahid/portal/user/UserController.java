package com.shaahid.portal.user;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ============================================================================
 * REST CONTROLLER: USER API
 * Group Member: Shaahid (Member #1)
 * Package: com.shaahid.portal.user
 * Base URL: /api/users
 * ============================================================================
 * 
 * Endpoints:
 *   POST   /api/users/signup   -> Registers a new user account
 *   POST   /api/users/login    -> Authenticates user credentials
 *   GET    /api/users          -> Returns all users (JSON array)
 *   GET    /api/users/{id}     -> Returns a specific user by ID
 *   PUT    /api/users/{id}     -> Updates user details
 *   DELETE /api/users/{id}     -> Removes user account
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    // Instance of UserManager providing CRUD business logic
    private UserManager manager = new UserManager();

    /**
     * User registration endpoint.
     * Validates required inputs (name, NIC, email, password) and checks for email uniqueness.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (isEmpty(user.getName()) || isEmpty(user.getNic()) || isEmpty(user.getEmail()) || isEmpty(user.getPassword())) {
            return ResponseEntity.badRequest().body("Please fill in name, NIC, email and password");
        }
        if (manager.emailExists(user.getEmail())) {
            return ResponseEntity.badRequest().body("This email is already registered");
        }
        return ResponseEntity.ok(manager.addUser(user));
    }

    /**
     * User authentication endpoint.
     * Verifies provided email and password.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData) {
        User user = manager.login(loginData.getEmail(), loginData.getPassword());
        if (user == null) {
            return ResponseEntity.status(401).body("Wrong email or password");
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves all registered users.
     */
    @GetMapping
    public List<User> getAll() {
        return manager.getAllUsers();
    }

    /**
     * Retrieves a single user by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        User user = manager.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Updates an existing user's information.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody User user) {
        User updated = manager.updateUser(id, user);
        if (updated == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a user account by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (manager.deleteUser(id)) {
            return ResponseEntity.ok("User deleted");
        }
        return ResponseEntity.status(404).body("User not found");
    }

    // Helper: Validates whether a text string is null or whitespace
    private boolean isEmpty(String text) {
        return text == null || text.isBlank();
    }
}
