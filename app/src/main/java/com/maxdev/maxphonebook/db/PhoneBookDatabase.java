package com.maxdev.maxphonebook.db;


import android.content.Context;

import com.maxdev.maxphonebook.db.contactgroup.ContactGroup;
import com.maxdev.maxphonebook.db.contactgroup.ContactGroupDAO;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconColor;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconDAO;
import com.maxdev.maxphonebook.db.contacts.Contact;
import com.maxdev.maxphonebook.db.contacts.ContactDAO;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Contact.class, ContactIconColor.class, ContactGroup.class}, version = 3)
public abstract class PhoneBookDatabase extends RoomDatabase {
    public abstract ContactDAO contactDAO();
    public abstract ContactIconDAO contactIconDAO();
    public abstract ContactGroupDAO contactGroupDAO();
    private static String dbName = "PhoneBook";
    private static PhoneBookDatabase sInstance;
    static final Migration MIGRATION = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE 'ContactGroups' ('id' INTEGER PRIMARY KEY, 'name' TEXT)");
            database.execSQL("ALTER TABLE 'Contacts' ADD COLUMN groupId Integer REFERENCES ContactGroups(id)");
        }
    };
    public static synchronized PhoneBookDatabase getDatabaseInstance(Context context) {
        if(sInstance == null) {
            sInstance = create(context);
        }
        return sInstance;
    }

    private static PhoneBookDatabase create(Context context) {
        return Room.databaseBuilder(context, PhoneBookDatabase.class, dbName)
                .fallbackToDestructiveMigration().build();
    }
}
