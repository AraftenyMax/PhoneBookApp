package com.maxdev.maxphonebook.contacts.list;

import com.maxdev.maxphonebook.App;
import com.maxdev.maxphonebook.db.contacts.Contact;
import com.maxdev.maxphonebook.db.contacts.ContactsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class ContactsListPresenter {
    private View view;
    @Inject
    ContactsRepository contactsRepository;

    public ContactsListPresenter(View view) {
        App.getPhoneBookComponent().inject(this);
        this.view = view;
        initContactsList();
    }

    private void initContactsList() {
        contactsRepository.getContacts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.view::onContactsListFetched);
    }

    public interface View {
        void onContactsListFetched(List<Contact> contactList);
    }
}
