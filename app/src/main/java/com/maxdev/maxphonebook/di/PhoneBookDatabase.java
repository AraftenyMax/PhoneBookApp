package com.maxdev.maxphonebook.di;


import android.content.Context;

import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconDAO;
import com.maxdev.maxphonebook.db.contacts.Contact;
import com.maxdev.maxphonebook.db.contacts.ContactDAO;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class PhoneBookDatabase extends RoomDatabase {
    public abstract ContactDAO contactDAO();
    public abstract ContactIconDAO contactIconDAO();
    private static String dbName = "PhoneBook";
    private static PhoneBookDatabase sInstance;
    public static synchronized PhoneBookDatabase getDatabaseInstance(Context context) {
        if(sInstance == null) {
            sInstance = create(context);
        }
        return sInstance;
    }

    private static PhoneBookDatabase create(Context context) {
        return Room.databaseBuilder(context, PhoneBookDatabase.class, dbName).build();
    }
}
