package com.maxdev.maxphonebook.contacts.single;


import com.maxdev.maxphonebook.App;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconColor;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconRepository;
import com.maxdev.maxphonebook.db.contacts.Contact;
import com.maxdev.maxphonebook.db.contacts.ContactsRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactDetailedPresenter {
    private View view;
    @Inject
    ContactIconRepository contactIconRepository;
    @Inject
    ContactsRepository contactsRepository;


    public ContactDetailedPresenter(View view, Contact contact) {
        App.getPhoneBookComponent().inject(this);
        this.view = view;
    }

    public void deleteContact(Contact contact) {
        contactsRepository.deleteContact(contact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.view::onContactDeleteSuccess, Throwable::printStackTrace);
    }

    private void loadIcon(Contact contact) {
        contactIconRepository.select(contact.getFirstChars())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (color) -> {view.onContactLoaded(contact, color);},
                        Throwable::printStackTrace);
    }

    public interface View {
        void onContactLoaded(Contact contact, ContactIconColor color);
        void onContactDeleteSuccess();
    }
}
