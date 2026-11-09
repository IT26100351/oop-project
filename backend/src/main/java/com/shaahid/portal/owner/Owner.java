package com.shaahid.portal.owner;

import com.shaahid.portal.common.FileHelper;

// The person you CALL about a property. Saved in data/owners.txt
public class Owner {

    private String id; // e.g. O001
    private String name;
    private int phone; // used by the "Call" button
    private String userId; // the user account that created this owner

    public Owner() {
    }

    public Owner(String id, String name, int phone, String userId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.userId = userId;
    }

    // One line for the file: id|name|phone|userId
    public String toFileString() {
        return id + "|" + FileHelper.clean(name) + "|" + phone + "|" + FileHelper.clean(userId);
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
