package com.maxdev.maxphonebook.contacts;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.maxdev.maxphonebook.R;
import com.maxdev.maxphonebook.contacticonhelper.ContactIconHelper;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconColor;
import com.maxdev.maxphonebook.db.contacts.Contact;

import java.text.ParseException;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContactEditBaseFragment extends Fragment {
    protected View view;
    protected EditText firstNameEdit;
    protected EditText lastNameEdit;
    protected EditText phoneEdit;
    protected EditText emailEdit;
    protected EditText homeAddressEdit;
    protected TextInputLayout firstNameInputLayout;
    protected TextInputLayout lastNameInputLayout;
    protected TextInputLayout emailInputLayout;
    protected EditText dateOfBirthEdit;
    protected TextView contactIconPreviewView;

    protected String getNameInitials() throws IndexOutOfBoundsException {
        String firstName = firstNameEdit.getText().toString();
        String lastName = lastNameEdit.getText().toString();
        String chars = String.format("%c%c", firstName.charAt(0), lastName.charAt(0));
        return chars.toUpperCase();
    }

    protected Contact getContact() throws ParseException {
        String firstName = firstNameEdit.getText().toString();
        String lastName = lastNameEdit.getText().toString();
        String phone = phoneEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String homeAddress = homeAddressEdit.getText().toString();
        String dateOfBirth = dateOfBirthEdit.getText().toString();
        Contact contact = new Contact(firstName, lastName, email, phone, dateOfBirth, homeAddress);
        return contact;
    }

    protected void contactValidationFailed(Map<String, String> errors) {
        if (errors.containsKey(ContactsValidator.firstNameField)) {
            firstNameInputLayout.setError(errors.get(ContactsValidator.firstNameField));
        }
        if (errors.containsKey(ContactsValidator.lastNameField)) {
            lastNameInputLayout.setError(errors.get(ContactsValidator.lastNameField));
        }
        if (errors.containsKey(ContactsValidator.emailField)) {
            emailInputLayout.setError(errors.get(ContactsValidator.emailField));
        }
    }

    protected void clearContactIcon() {
        contactIconPreviewView.setText("");
        contactIconPreviewView.setBackground(null);
    }

    protected void displayContactIcon(ContactIconColor color) {
        contactIconPreviewView.setText(color.getStartChars());
        Drawable background = ContactIconHelper.getIconBackground(
                color.getColor(), contactIconPreviewView.getWidth());
        contactIconPreviewView.setBackground(background);
    }

    protected void locateElements() {
        firstNameEdit = (EditText) view.findViewById(R.id.firstNameEdit);
        lastNameEdit = (EditText) view.findViewById(R.id.lastNameEdit);
        phoneEdit = (EditText) view.findViewById(R.id.phoneNumberEdit);
        emailEdit = (EditText) view.findViewById(R.id.emailEdit);
        dateOfBirthEdit = (EditText) view.findViewById(R.id.dateOfBirthEdit);
        homeAddressEdit = (EditText) view.findViewById(R.id.homeAddressEdit);
        contactIconPreviewView = (TextView) view.findViewById(R.id.contactIconPreviewView);

        firstNameInputLayout = (TextInputLayout) view.findViewById(R.id.firstNameInputLayout);
        lastNameInputLayout = (TextInputLayout) view.findViewById(R.id.lastNameInputLayout);
        emailInputLayout = (TextInputLayout) view.findViewById(R.id.emailInputLayout);
    }

    public ContactEditBaseFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
