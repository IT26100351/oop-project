package com.shaahid.portal.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shaahid.portal.common.FileHelper;

// A person with an account. Saved in data/users.txt
public class User {

    private String id;        // e.g. U001
    private String name;
    private String nic;       // National Identity Card number
    private String email;

    // WRITE_ONLY = the password can be RECEIVED from the frontend,
    // but is never SENT back to it (so it can't be seen in the browser).
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public User() {
    }

    public User(String id, String name, String nic, String email, String password) {
        this.id = id;
        this.name = name;
        this.nic = nic;
        this.email = email;
        this.password = password;
    }

    // One line for the file: id|name|nic|email|password
    public String toFileString() {
        return id + "|" + FileHelper.clean(name) + "|" + FileHelper.clean(nic) + "|"
                + FileHelper.clean(email) + "|" + FileHelper.clean(password);
    }

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
