package com.maxdev.maxphonebook.db.contactgroup;

import java.security.PublicKey;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class ContactGroupsRepository {
    private final ContactGroupDAO contactGroupDAO;

    @Inject
    public ContactGroupsRepository(ContactGroupDAO dao) {
        this.contactGroupDAO = dao;
    }

    public Single<ContactGroup> select(String name) {
        return contactGroupDAO.select(name);
    }

    public Flowable<ContactGroup> selectAll() {
        return contactGroupDAO.selectAll();
    }

    public Completable insert(ContactGroup group) {
        return contactGroupDAO.insert(group);
    }

    public Completable update(ContactGroup group) {
        return contactGroupDAO.update(group);
    }

    public Completable delete(ContactGroup group) {
        return contactGroupDAO.delete(group);
    }
}
