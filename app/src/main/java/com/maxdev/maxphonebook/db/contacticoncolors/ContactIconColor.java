package com.maxdev.maxphonebook.db.contacticoncolors;

import androidx.room.Entity;

@Entity(tableName = "ContactIconColors")
public class ContactIconColor {
    private String color;
    private String startChars;

    public ContactIconColor(String color, String startChars) {
        this.color = color;
        this.startChars = startChars;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStartChars() {
        return startChars;
    }

    public void setStartChars(String startChars) {
        this.startChars = startChars;
    }
}
