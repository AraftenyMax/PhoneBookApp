package com.maxdev.maxphonebook.contacts.add;

import android.graphics.Color;

import com.maxdev.maxphonebook.App;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconColor;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconRepository;
import com.maxdev.maxphonebook.db.contacts.Contact;
import com.maxdev.maxphonebook.db.contacts.ContactsRepository;
import com.maxdev.maxphonebook.contacts.ContactsValidator;

import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ContactsAddPresenter {
    private static final String TAG = "ContactAddPresenter";
    private Random random = new Random();
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
        if (ContactsValidator.isValid(contact)) {
            contactsRepository.createContact(contact)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this.view::onContactSavedSuccessfully, this.view::onContactSaveFailed);
        } else {
            Map<String, String> errors = ContactsValidator.getErrors();
            view.onContactValidationFailed(errors);
        }
    }

    public void selectColor(String startChars) {
        if (ContactsValidator.validateNameInitials(startChars)) {
            contactIconRepository.select(startChars)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ContactIconColor>() {
                @Override
                public void accept(ContactIconColor iconColor) throws Exception {
                    view.onDisplayContactIcon(iconColor);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    ContactIconColor iconColor = generateColor(startChars);
                    Completable completable = contactIconRepository.insert(iconColor);
                    completable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> view.onDisplayContactIcon(iconColor), Throwable::printStackTrace);
                }
            });
        } else {
            view.onClearContactIcon();
        }
    }

    private ContactIconColor generateColor(String startChars) {
        int color = Color.argb(255,
                random.nextInt(255), random.nextInt(255), random.nextInt(255));
        return new ContactIconColor(color, startChars);
    }

    public interface View {
        void onContactSave();

        void onContactSavedSuccessfully();

        void onContactValidationFailed(Map<String, String> errors);

        void onContactSaveFailed(Throwable throwable);

        void onDisplayContactIcon(ContactIconColor color);

        void onClearContactIcon();
    }
}
