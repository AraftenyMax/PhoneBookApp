package com.maxdev.maxphonebook.contacts.list;

import com.maxdev.maxphonebook.App;
import com.maxdev.maxphonebook.contacts.ContactsRepository;

import javax.inject.Inject;

import androidx.fragment.app.FragmentManager;

public class ContactsListPresenter {
    private FragmentManager fragmentManager;
    @Inject
    ContactsRepository contactsRepository;

    public ContactsListPresenter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        App.getPhoneBookComponent().inject(this);
    }
}
