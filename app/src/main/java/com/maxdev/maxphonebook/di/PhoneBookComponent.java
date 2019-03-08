package com.maxdev.maxphonebook.di;

import com.maxdev.maxphonebook.contacts.add.ContactsAddPresenter;
import com.maxdev.maxphonebook.contacts.list.ContactsListPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = PhoneBookDatabaseModule.class)
@Singleton
public interface PhoneBookComponent {
    void inject(ContactsAddPresenter presenter);
    void inject(ContactsListPresenter presenter);
}
