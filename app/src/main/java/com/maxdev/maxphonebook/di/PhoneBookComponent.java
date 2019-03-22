package com.maxdev.maxphonebook.di;

import com.maxdev.maxphonebook.contacts.add.ContactsAddPresenter;
import com.maxdev.maxphonebook.contacts.list.ContactsListPresenter;
import com.maxdev.maxphonebook.contacts.detailed.ContactDetailedPresenter;
import com.maxdev.maxphonebook.contacts.update.ContactUpdatePresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = PhoneBookDatabaseModule.class)
@Singleton
public interface PhoneBookComponent {
    void inject(ContactsAddPresenter presenter);
    void inject(ContactsListPresenter presenter);
    void inject(ContactDetailedPresenter presenter);
    void inject(ContactUpdatePresenter presenter);
}
