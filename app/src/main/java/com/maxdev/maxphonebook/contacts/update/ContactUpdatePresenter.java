package com.maxdev.maxphonebook.contacts.update;

import com.maxdev.maxphonebook.App;
import com.maxdev.maxphonebook.contacticonhelper.ContactIconHelper;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconColor;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconRepository;
import com.maxdev.maxphonebook.db.contacts.Contact;
import com.maxdev.maxphonebook.db.contacts.ContactsRepository;
import com.maxdev.maxphonebook.contacts.ContactsValidator;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ContactUpdatePresenter {
    private View view;
    private Contact contact;
    @Inject
    ContactsRepository contactsRepository;
    @Inject
    ContactIconRepository contactIconRepository;

    public ContactUpdatePresenter(View view, Contact contact) {
        App.getPhoneBookComponent().inject(this);
        this.view = view;
        this.contact = contact;
    }

    public void updateContact(Contact contact) {
        if (ContactsValidator.isValid(contact)) {
            this.contact = contact;
            contactsRepository.updateContact(contact)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this.view::onContactUpdatedSuccessfully,
                            this.view::onContactUpdateFail);
        } else {
            view.onContactValidationFailed(ContactsValidator.getErrors());
        }
    }

    public void updateContactIcon(String startChars) {
        if (ContactsValidator.validateNameInitials(startChars)) {
            contactIconRepository.select(startChars)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ContactIconColor>() {
                        @Override
                        public void accept(ContactIconColor color) throws Exception {
                            view.onUpdateIconSuccess(color);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            ContactIconColor iconColor = ContactIconHelper.generateColor(startChars);
                            contactIconRepository.insert(iconColor)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        view.onUpdateIconSuccess(iconColor);
                                    }, throwable1 -> {
                                        view.onUpdateIconFail(throwable1);
                                    });
                        }
                    });
        } else {
            view.onContactIconClear();
        }
    }

    public interface View {
        void onContactUpdatedSuccessfully();

        void onContactUpdateFail(Throwable throwable);

        void onContactValidationFailed(Map<String, String> errors);

        void onUpdateIconSuccess(ContactIconColor color);

        void onUpdateIconFail(Throwable throwable);

        void onContactIconClear();
    }
}
