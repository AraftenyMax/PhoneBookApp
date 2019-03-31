package com.maxdev.maxphonebook.db.contactgroup;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ContactGroups")
public class ContactGroup {
    @PrimaryKey
    private int id;
    private String name;

    public ContactGroup(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @Ignore
    public ContactGroup(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
