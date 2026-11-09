package com.shaahid.portal.user;

import com.shaahid.portal.common.FileHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * BUSINESS LOGIC & DATA ACCESS LAYER: USER MANAGEMENT
 * Group Member: Shaahid (Member #1)
 * Package: com.shaahid.portal.user
 * Data Store: data/users.txt
 * ============================================================================
 * 
 * Implements complete CRUD operations and authentication routines for users:
 * 1. CREATE (addUser / signup)
 * 2. READ (getAllUsers, getUserById)
 * 3. UPDATE (updateUser)
 * 4. DELETE (deleteUser)
 * 5. AUTHENTICATION (login, emailExists)
 */
public class UserManager {

    private static final String FILE = "data/users.txt";

    /**
     * Converts a single text line from data/users.txt into a User object.
     *
     * @param line Pipe-separated string record.
     * @return User object.
     */
    private User lineToUser(String line) {
        String[] parts = line.split("\\|", -1);
        return new User(
            parts[0], 
            parts[1], 
            parts[2], 
            parts[3], 
            parts.length > 4 ? parts[4] : ""
        );
    }

    /**
     * Overwrites data/users.txt with the entire current list of users.
     *
     * @param users Complete list of users.
     */
    private void saveAll(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User user : users) {
            lines.add(user.toFileString());
        }
        FileHelper.writeLines(FILE, lines);
    }

    /**
     * READ ALL: Retrieves all registered users from file storage.
     *
     * @return List of User objects.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (String line : FileHelper.readLines(FILE)) {
            users.add(lineToUser(line));
        }
        return users;
    }

    /**
     * READ ONE: Finds a user by unique identifier.
     *
     * @param id User ID (e.g., "U001").
     * @return User object if found, or null otherwise.
     */
    public User getUserById(String id) {
        for (User user : getAllUsers()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Checks whether an email address is already registered in the system (case-insensitive).
     *
     * @param email Email address to check.
     * @return true if email already exists, false otherwise.
     */
    public boolean emailExists(String email) {
        if (email == null) return false;
        for (User user : getAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * CREATE: Registers a new user account with auto-generated ID (e.g., U001).
     *
     * @param user User object containing registration details.
     * @return Created User object with assigned ID.
     */
    public User addUser(User user) {
        List<String> lines = FileHelper.readLines(FILE);
        user.setId(FileHelper.nextId("U", lines));
        lines.add(user.toFileString());
        FileHelper.writeLines(FILE, lines);
        return user;
    }

    /**
     * AUTHENTICATE: Verifies login credentials against stored records.
     *
     * @param email User's email.
     * @param password Plain-text password provided.
     * @return User object on successful match, or null on failed verification.
     */
    public User login(String email, String password) {
        if (email == null || password == null) {
            return null;
        }
        for (User user : getAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email.trim()) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * UPDATE: Modifies details of an existing user.
     *
     * @param id Target User ID to update.
     * @param newData Updated user fields.
     * @return Updated User object, or null if target ID was not found.
     */
    public User updateUser(String id, User newData) {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                newData.setId(id);
                // Retain previous password if none is provided in update
                if (newData.getPassword() == null || newData.getPassword().isBlank()) {
                    newData.setPassword(users.get(i).getPassword());
                }
                users.set(i, newData);
                saveAll(users);
                return newData;
            }
        }
        return null;
    }

    /**
     * DELETE: Removes a user from file storage.
     *
     * @param id User ID to remove.
     * @return true if deleted, false if user was not found.
     */
    public boolean deleteUser(String id) {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                saveAll(users);
                return true;
            }
        }
        return false;
    }
}
