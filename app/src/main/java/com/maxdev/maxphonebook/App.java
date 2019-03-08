package com.maxdev.maxphonebook;

import android.app.Application;
import android.util.Log;

import com.maxdev.maxphonebook.di.DaggerPhoneBookComponent;
import com.maxdev.maxphonebook.di.PhoneBookComponent;
import com.maxdev.maxphonebook.di.PhoneBookDatabaseModule;

public class App extends Application {
    private static PhoneBookComponent phoneBookComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        phoneBookComponent = DaggerPhoneBookComponent.builder()
                .phoneBookDatabaseModule(new PhoneBookDatabaseModule(getApplicationContext()))
                .build();
    }

    public static PhoneBookComponent getPhoneBookComponent() {
        return phoneBookComponent;
    }
}
