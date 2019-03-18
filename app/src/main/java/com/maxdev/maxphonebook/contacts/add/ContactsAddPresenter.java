package com.maxdev.maxphonebook.contacts.add;

import android.graphics.Color;
import android.util.Log;

import com.maxdev.maxphonebook.App;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconColor;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconRepository;
import com.maxdev.maxphonebook.db.contacts.Contact;
import com.maxdev.maxphonebook.db.contacts.ContactsRepository;
import com.maxdev.maxphonebook.db.contacts.ContactsValidator;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
        Observable.fromCallable(() -> {
            if (!ContactsValidator.validate(contact))
                Observable.error(new IllegalArgumentException());
            return contact;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Contact>() {
                    @Override
                    public void accept(Contact contact) throws Exception {
                        contactsRepository.createContact(contact);
                        view.onContactSavedSuccessfully();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.onContactSaveFailed(throwable);
                    }
                });
    }

    public void selectColor(String startChars) {
        if (ContactsValidator.validateNamePart(startChars)) {
            contactIconRepository.select(startChars)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ContactIconColor>() {
                @Override
                public void accept(ContactIconColor iconColor) throws Exception {
                    view.displayContactIcon(iconColor);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    ContactIconColor iconColor = generateColor(startChars);
                    Completable completable = contactIconRepository.insert(iconColor);
                    completable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> view.displayContactIcon(iconColor), Throwable::printStackTrace);
                }
            });
        } else {
            view.clearContactIcon();
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

        void onContactValidationFailed();

        void onContactSaveFailed(Throwable throwable);

        void displayContactIcon(ContactIconColor color);

        void clearContactIcon();
    }
}
