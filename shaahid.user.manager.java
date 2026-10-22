package com.shaahid.portal.user;

import com.shaahid.portal.common.FileHelper;
import java.util.ArrayList;
import java.util.List;

// All the CRUD work for users, plus signup and login. Data file: data/users.txt
public class UserManager {

    private static final String FILE = "data/users.txt";

    private User lineToUser(String line) {
        String[] parts = line.split("\\|", -1);
        return new User(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    private void saveAll(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User user : users) {
            lines.add(user.toFileString());
        }
        FileHelper.writeLines(FILE, lines);
    }

    // READ ALL
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        for (String line : FileHelper.readLines(FILE)) {
            users.add(lineToUser(line));
        }
        return users;
    }

    // READ ONE
    public User getUserById(String id) {
        for (User user : getAllUsers()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    // Is this email already used by someone?
    public boolean emailExists(String email) {
        for (User user : getAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    // CREATE (this is the "signup")
    public User addUser(User user) {
        List<String> lines = FileHelper.readLines(FILE);
        user.setId(FileHelper.nextId("U", lines));
        lines.add(user.toFileString());
        FileHelper.writeLines(FILE, lines);
        return user;
    }

    // LOGIN: find the user whose email AND password both match. Returns null if none.
    public User login(String email, String password) {
        if (email == null || password == null) {
            return null;
        }
        for (User user : getAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // UPDATE
    public User updateUser(String id, User newData) {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                newData.setId(id);
                // if no new password was typed, keep the old one
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

    // DELETE
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
