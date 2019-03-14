package com.maxdev.maxphonebook.db.contacts;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Singleton
public class ContactsRepository {
    private final ContactDAO contactDAO;

    @Inject
    public ContactsRepository(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    public Single<Contact> getContact(int userId) {
        return contactDAO.getContact(userId);
    }

    public Flowable<List<Contact>> getContacts() {
        return contactDAO.getPage();
    }

    public Completable createContact(Contact contact) {
        return contactDAO.save(contact);
    }

    public Completable deleteContact(Contact contact) {
        return contactDAO.deleteContact(contact);
    }

    public Completable updateContact(Contact contact) {
        return contactDAO.updateContact(contact);
    }

}
