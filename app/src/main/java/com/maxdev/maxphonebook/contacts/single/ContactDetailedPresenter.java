package com.maxdev.maxphonebook.contacts.single;


import com.maxdev.maxphonebook.App;
import com.maxdev.maxphonebook.db.contacts.Contact;
import com.maxdev.maxphonebook.db.contacts.ContactsRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactDetailedPresenter {
    private View view;
    @Inject
    ContactsRepository repository;

    public ContactDetailedPresenter(View view) {
        App.getPhoneBookComponent().inject(this);
        this.view = view;
    }

    public void loadContact(int id) {
        repository.getContact(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.view::onContactLoaded, Throwable::printStackTrace);
    }

    public void deleteContact(Contact contact) {
        repository.deleteContact(contact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.view::onContactDeleteSuccess, Throwable::printStackTrace);
    }

    public interface View {
        void onContactLoaded(Contact contact);
        void onContactDeleteSuccess();
    }
}
