package com.maxdev.maxphonebook;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.maxdev.maxphonebook.db.contacts.Contact;
import com.maxdev.maxphonebook.di.DaggerPhoneBookComponent;
import com.maxdev.maxphonebook.di.PhoneBookComponent;
import com.maxdev.maxphonebook.di.PhoneBookDatabaseModule;


public class App extends Application {
    private static PhoneBookComponent phoneBookComponent;
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        phoneBookComponent = DaggerPhoneBookComponent.builder()
                .phoneBookDatabaseModule(new PhoneBookDatabaseModule(getApplicationContext()))
                .build();
        mContext = this;
    }

    public static PhoneBookComponent getPhoneBookComponent() {
        return phoneBookComponent;
    }
    public static Context getAppContext() { return mContext; }
    public static Resources getAppResources() { return mContext.getResources(); }
}
