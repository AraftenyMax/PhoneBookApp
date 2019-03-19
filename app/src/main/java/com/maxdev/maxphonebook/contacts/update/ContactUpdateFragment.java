package com.maxdev.maxphonebook.contacts.update;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.maxdev.maxphonebook.R;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconColor;
import com.maxdev.maxphonebook.db.contacts.Contact;

public class ContactUpdateFragment extends Fragment implements ContactUpdatePresenter.View{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CONTACT_PARAM = "param1";
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
            presenter.updateContact(contact);
        }
    };

    private View

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
        return inflater.inflate(R.layout.fragment_contact_update, container, false);
    }

    private void locateElements() {

    }

    private Contact collectContact() {
        return null;
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
