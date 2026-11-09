package com.shaahid.portal.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shaahid.portal.common.FileHelper;

/**
 * ============================================================================
 * OOP CONCEPT: ENCAPSULATION & DATA MODELING
 * Group Member: Shaahid (Member #1)
 * Package: com.shaahid.portal.user
 * Data Store: data/users.txt
 * ============================================================================
 * 
 * Represents a registered user account in the HomeFinder portal.
 * 
 * ENCAPSULATION:
 * - All user attributes (id, name, nic, email, password) are strictly private.
 * - Access is exposed via public getters and setters.
 * 
 * SECURITY:
 * - The password attribute uses @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
 *   so that passwords can be received from client requests during signup/login,
 *   but are never serialized back in HTTP JSON responses to the frontend.
 */
public class User {

    // Unique user identifier (e.g., U001, U002)
    private String id;

    // Full name of the user
    private String name;

    // Sri Lankan National Identity Card (NIC) number
    private String nic;

    // Unique email address used as login username
    private String email;

    // User password (WRITE_ONLY prevents exposure in frontend JSON responses)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // Default constructor required for JSON deserialization
    public User() {
    }

    // Parameterized constructor
    public User(String id, String name, String nic, String email, String password) {
        this.id = id;
        this.name = name;
        this.nic = nic;
        this.email = email;
        this.password = password;
    }

    /**
     * Serializes this User object into a pipe-delimited record for users.txt.
     * Format: id|name|nic|email|password
     *
     * @return Pipe-separated string.
     */
    public String toFileString() {
        return id + "|" 
                + FileHelper.clean(name) + "|" 
                + FileHelper.clean(nic) + "|"
                + FileHelper.clean(email) + "|" 
                + FileHelper.clean(password);
    }

    // =========================================================================
    // ENCAPSULATION: Getters and Setters
    // =========================================================================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
