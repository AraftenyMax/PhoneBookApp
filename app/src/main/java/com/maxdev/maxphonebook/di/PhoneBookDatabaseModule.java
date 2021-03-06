package com.maxdev.maxphonebook.di;

import android.content.Context;

import com.maxdev.maxphonebook.db.PhoneBookDatabase;
import com.maxdev.maxphonebook.db.contactgroup.ContactGroupsRepository;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconRepository;
import com.maxdev.maxphonebook.db.contacts.ContactsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PhoneBookDatabaseModule {
    private static PhoneBookDatabase database;
    public PhoneBookDatabaseModule(Context context) {
        database = PhoneBookDatabase.getDatabaseInstance(context);
    }

    @Singleton
    @Provides static PhoneBookDatabase providePhoneBookDatabase() {
        return database;
    }

    @Provides static ContactsRepository provideContactsRepository() {
        return new ContactsRepository(database.contactDAO());
    }

    @Provides static ContactIconRepository provideContactIconColorRepository() {
        return new ContactIconRepository(database.contactIconDAO());
    }

    @Provides static ContactGroupsRepository provideContactGroupsRepository() {
        return new ContactGroupsRepository(database.contactGroupDAO());
    }
}
