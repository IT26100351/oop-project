package com.shaahid.portal.user;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// URLs for the user part:
//   POST   /api/users/signup   -> create an account
//   POST   /api/users/login    -> log in (email + password)
//   GET    /api/users          -> all users
//   GET    /api/users/U001     -> one user
//   PUT    /api/users/U001     -> update
//   DELETE /api/users/U001     -> delete
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserManager manager = new UserManager();

    // SIGNUP: needs name, NIC, email and password
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

    // LOGIN: needs only email and password
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData) {
        User user = manager.login(loginData.getEmail(), loginData.getPassword());
        if (user == null) {
            return ResponseEntity.status(401).body("Wrong email or password");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public List<User> getAll() {
        return manager.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        User user = manager.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody User user) {
        User updated = manager.updateUser(id, user);
        if (updated == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (manager.deleteUser(id)) {
            return ResponseEntity.ok("User deleted");
        }
        return ResponseEntity.status(404).body("User not found");
    }

    // small helper: true when text is missing or only spaces
    private boolean isEmpty(String text) {
        return text == null || text.isBlank();
    }
}
