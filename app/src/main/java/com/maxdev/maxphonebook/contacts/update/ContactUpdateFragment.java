package com.maxdev.maxphonebook.contacts.update;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maxdev.maxphonebook.R;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconColor;
import com.maxdev.maxphonebook.db.contacts.Contact;
import com.maxdev.maxphonebook.utils.DateFormatter;

import java.text.ParseException;
import java.util.Date;

public class ContactUpdateFragment extends Fragment implements ContactUpdatePresenter.View{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CONTACT_PARAM = "param1";
    private static final String TAG = "ContactUpdateFragment";
    private View view;
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText emailEdit;
    private EditText phoneEdit;
    private EditText homeAddressEdit;
    private EditText dateOfBirthEdit;
    private Button updateContact;
    private ContactUpdatePresenter presenter;

    private View.OnClickListener onUpdateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Contact contact = collectContact();
            try {
                presenter.updateContact(contact);
            } catch (IllegalArgumentException ex) {
                Log.e(TAG, ex.getMessage());
            }
        }
    };

    private View.OnFocusChangeListener onNameEditsFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(!hasFocus) {
                char firstNameInitial = firstNameEdit.getText().toString().charAt(0);
                char lastNameInitial = lastNameEdit.getText().toString().charAt(0);
                String nameInitials = String.format("%c%c", firstNameInitial, lastNameInitial);
                presenter.updateContactIcon(nameInitials);
            }
        }
    };

    // TODO: Rename and change types of parameters
    private Contact mContact;

    public ContactUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ContactUpdatePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_update, container, false);
        locateElements();
        return view;
    }

    private void locateElements() {
        firstNameEdit = (EditText) view.findViewById(R.id.firstNameEdit);
        lastNameEdit = (EditText) view.findViewById(R.id.lastNameEdit);
        emailEdit = (EditText) view.findViewById(R.id.emailEdit);
        phoneEdit = (EditText) view.findViewById(R.id.phoneNumberEdit);
        homeAddressEdit = (EditText) view.findViewById(R.id.homeAddressEdit);
        dateOfBirthEdit = (EditText) view.findViewById(R.id.dateOfBirthEdit);
        updateContact = (Button) view.findViewById(R.id.updateContactButton);
        updateContact.setOnClickListener(onUpdateClickListener);
        firstNameEdit.setOnFocusChangeListener(onNameEditsFocusListener);
        lastNameEdit.setOnFocusChangeListener(onNameEditsFocusListener);
    }

    private Contact collectContact() throws IllegalArgumentException {
        Date dateOfBirth;
        String firstName = firstNameEdit.getText().toString();
        String lastName = lastNameEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String phone = phoneEdit.getText().toString();
        String homeAddress = homeAddressEdit.getText().toString();
        try {
            dateOfBirth = DateFormatter.fromString(dateOfBirthEdit.getText().toString());
        } catch (ParseException ex) {
            Toast.makeText(getContext(), getString(R.string.invalidDateFormat), Toast.LENGTH_SHORT).show();
            throw new IllegalArgumentException(getString(R.string.invalidDateFormat));
        }
        return new Contact(firstName, lastName, phone, email, homeAddress, dateOfBirth);
    }

    @Override
    public void onContactUpdatedSuccessfully() {

    }

    @Override
    public void onContactUpdateFail(Throwable throwable) {

    }

    @Override
    public void onContactValidationFailed() {

    }

    @Override
    public void updateIconSuccess(ContactIconColor color) {

    }

    @Override
    public void updateIconFail(Throwable throwable) {

    }
}
