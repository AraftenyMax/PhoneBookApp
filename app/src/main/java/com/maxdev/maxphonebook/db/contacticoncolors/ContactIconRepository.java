package com.maxdev.maxphonebook.db.contacticoncolors;

import com.maxdev.maxphonebook.db.contacts.Contact;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class ContactIconRepository {
    private final ContactIconDAO contactIconDAO;

    @Inject
    public ContactIconRepository(ContactIconDAO dao) {
        this.contactIconDAO = dao;
    }

    public Single<ContactIconColor> select(String startChars){
        return contactIconDAO.select(startChars);
    }

    public Completable insert(ContactIconColor color) {
        return contactIconDAO.insert(color);
    }

    public Completable update(ContactIconColor color) {
        return contactIconDAO.update(color);
    }

    public Completable delete(ContactIconColor color) {
        return contactIconDAO.delete(color);
    }
}
