package com.maxdev.maxphonebook.contacts.add;

import com.maxdev.maxphonebook.App;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconColor;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconRepository;
import com.maxdev.maxphonebook.db.contacts.Contact;
import com.maxdev.maxphonebook.db.contacts.ContactsRepository;

import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ContactsAddPresenter {
    @Inject
    ContactIconRepository contactIconRepository;
    @Inject
    ContactsRepository contactsRepository;
    private View view;

    public ContactsAddPresenter(View view) {
        App.getPhoneBookComponent().inject(this);
        this.view = view;
    }

    public void saveContact(Contact contact) {
        contactsRepository.createContact(contact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.view::onContactSavedSuccessfully, this.view::onContactSaveFailed);
    }

    public int selectColor(String startChars) {
        Single.fromCallable(() -> contactIconRepository.select(startChars))
                .doOnSuccess(contactIconColorSingle -> {
                    ContactIconColor color = contactIconColorSingle.blockingGet();
                    if (color == null)
                        Observable.error(new NoSuchElementException(""));
                    view.displayContactIcon(color);
                }).doOnError(throwable -> {
            insertColor(startChars);

        });
    }

    private ContactIconColor insertColor(String startChars) {
        ContactIconColor color =
    }

    public interface View {
        void onContactSave();

        void onContactSavedSuccessfully();

        void onContactValidationFailed();

        void onContactSaveFailed(Throwable throwable);

        void displayContactIcon(ContactIconColor color);
    }
}
