package com.maxdev.maxphonebook.contacts.add;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maxdev.maxphonebook.R;
import com.maxdev.maxphonebook.db.contacts.Contact;
import com.maxdev.maxphonebook.db.contacts.ContactsValidator;
import com.maxdev.maxphonebook.utils.DateFormatter;

import java.text.ParseException;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class ContactAddFragment extends Fragment implements ContactsAddPresenter.View {
    EditText firstNameEdit;
    EditText lastNameEdit;
    EditText phoneEdit;
    EditText emailEdit;
    EditText homeAddressEdit;
    EditText dateOfBirthEdit;

    ContactsAddPresenter presenter;
    private View view;
    private View.OnFocusChangeListener onLastNameFocusChange =
            new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String firstName = firstNameEdit.toString();
                        String lastName = lastNameEdit.toString();
                        if (ContactsValidator.validateName(firstName, lastName)) {
                            String chars = String.format("%c%c", firstName.charAt(0), lastName.charAt(0));
                            presenter
                        }
                    }
                }
            };

    public ContactAddFragment() {
        // Required empty public constructor
    }

    public static ContactAddFragment newInstance() {
        ContactAddFragment fragment = new ContactAddFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ContactsAddPresenter(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_add, container, false);
        firstNameEdit = (EditText) view.findViewById(R.id.firstNameView);
        lastNameEdit = (EditText) view.findViewById(R.id.lastNameView);
        phoneEdit = (EditText) view.findViewById(R.id.phoneNumberView);
        emailEdit = (EditText) view.findViewById(R.id.emailView);
        dateOfBirthEdit = (EditText) view.findViewById(R.id.dateOfBirthView);
        homeAddressEdit = (EditText) view.findViewById(R.id.homeAddressView);
        Button addContactButton = (Button) view.findViewById(R.id.add_contact_button);
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContactSave();
            }
        });
        return view;
    }

    @Override
    public void onContactSave() {
        String firstName = firstNameEdit.getText().toString();
        String lastName = lastNameEdit.getText().toString();
        String phone = phoneEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String homeAddress = homeAddressEdit.toString();
        try {
            Date dateOfBirth = DateFormatter.fromString(dateOfBirthEdit.toString());
            Contact contact = new Contact(firstName, lastName, email, phone, homeAddress, dateOfBirth);
            presenter.saveContact(contact);
        } catch (ParseException ex) {
            showToast(getString(R.id.invalidDateFormat), Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onContactSavedSuccessfully() {
        Navigation.findNavController(view).navigate(R.id.contactsListFragment);
    }

    @Override
    public void onContactValidationFailed() {
        showToast(getString(R.string.invalidNameError), Toast.LENGTH_SHORT);
    }

    @Override
    public void onContactSaveFailed(Throwable throwable) {
        showToast(getString(R.string.save_contact_failed), Toast.LENGTH_SHORT);
    }

    public void showToast(String text, int duration) {
        Toast.makeText(getContext(), text, duration).show();
    }
}
