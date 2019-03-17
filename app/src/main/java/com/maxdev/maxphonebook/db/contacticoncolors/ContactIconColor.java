package com.maxdev.maxphonebook.db.contacticoncolors;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ContactIconColors")
public class ContactIconColor {
    private int color;
    @PrimaryKey
    private @NonNull
    String startChars;

    public ContactIconColor(int color, @NonNull String startChars) {
        this.color = color;
        this.startChars = startChars;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @NonNull
    public String getStartChars() {
        return startChars;
    }

    public void setStartChars(@NonNull String startChars) {
        this.startChars = startChars;
    }
}
