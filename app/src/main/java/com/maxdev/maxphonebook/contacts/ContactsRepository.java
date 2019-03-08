package com.maxdev.maxphonebook.contacts;

import android.app.Application;
import com.maxdev.maxphonebook.db.PhoneBookDatabase;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class ContactsRepository {
    private final ContactDAO contactDAO;

    @Inject
    public ContactsRepository(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    public Contact getContact(int userId) {
        return contactDAO.getContact(userId);
    }

    public List<Contact> getContacts(int page) {
        return contactDAO.getPage(page);
    }

    public Completable createContact(Contact contact) {
        return contactDAO.save(contact);
    }

    public int deleteContact(Contact contact) {
        return contactDAO.deleteContact(contact);
    }

    public int updateContact(Contact contact) {
        return contactDAO.updateContact(contact);
    }

}
