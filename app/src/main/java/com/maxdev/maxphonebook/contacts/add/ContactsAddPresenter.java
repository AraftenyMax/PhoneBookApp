package com.maxdev.maxphonebook.contacts.add;

import android.content.Context;

import com.maxdev.maxphonebook.App;
import com.maxdev.maxphonebook.R;
import com.maxdev.maxphonebook.contacts.Contact;
import com.maxdev.maxphonebook.contacts.ContactsRepository;
import com.maxdev.maxphonebook.contacts.list.ContactsListFragment;

import java.util.Observable;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class ContactsAddPresenter {
    private FragmentManager fragmentManager;
    @Inject
    ContactsRepository contactsRepository;
    Executor executor;

    public ContactsAddPresenter(FragmentManager fragmentManager) {
        App.getPhoneBookComponent().inject(this);
        this.fragmentManager = fragmentManager;
    }

    public void saveContact(Contact contact) {
        contactsRepository.createContact(contact).subscribeOn(Schedulers.io())
                .subscribe(this::backToContactsList, Throwable::printStackTrace).dispose();
    }

    private void backToContactsList() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentLayout, new ContactsListFragment());
        transaction.commit();
    }

    public interface View {
        void onContactSave(Contact contact);
    }
}
