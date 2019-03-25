package com.maxdev.maxphonebook.contacts.update;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.maxdev.maxphonebook.R;
import com.maxdev.maxphonebook.contacts.ContactEditBaseFragment;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconColor;
import com.maxdev.maxphonebook.db.contacts.Contact;
import com.maxdev.maxphonebook.utils.DateFormatter;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import androidx.navigation.Navigation;

public class ContactUpdateFragment extends ContactEditBaseFragment implements ContactUpdatePresenter.View{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Contact contact;
    private static final String TAG = "ContactUpdateFragment";
    private Button onUpdateContactButton;
    private ContactUpdatePresenter presenter;

    private View.OnClickListener onUpdateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                collectEditedContact(contact);
                presenter.updateContact(contact);
            } catch (IllegalArgumentException ex) {
                Log.e(TAG, ex.getMessage());
            } catch (ParseException ex) {
                Log.e(TAG, ex.getMessage());
                Toast.makeText(getContext(),
                        getString(R.string.invalidDateFormat), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void collectEditedContact(Contact contact) throws ParseException{
        contact.setFirstName(firstNameEdit.getText().toString());
        contact.setLastName(lastNameEdit.getText().toString());
        contact.setEmail(emailEdit.getText().toString());
        contact.setPhone(phoneEdit.getText().toString());
        contact.setHomeAddress(homeAddressEdit.getText().toString());
        Date dateOfBirth = DateFormatter.fromString(dateOfBirthEdit.getText().toString());
        if (dateOfBirth != null)
            contact.setDateOfBirth(dateOfBirth);
    }

    private View.OnFocusChangeListener onNameEditsFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(!hasFocus) {
                try {
                    String nameInitials = getNameInitials();
                    presenter.updateContactIcon(nameInitials);
                } catch (IndexOutOfBoundsException ex) {
                    Log.e(TAG, ex.getMessage());
                    clearContactIcon();
                }
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.contact = ContactUpdateFragmentArgs.fromBundle(getArguments()).getContact();
        presenter = new ContactUpdatePresenter(this, contact);
        view = inflater.inflate(R.layout.fragment_contact_update, container, false);
        locateElements();
        showContact(contact);
        return view;
    }

    @Override
    protected void locateElements() {
        super.locateElements();
        onUpdateContactButton = (Button) view.findViewById(R.id.updateContactFormButton);
        onUpdateContactButton.setOnClickListener(onUpdateClickListener);
        firstNameEdit.setOnFocusChangeListener(onNameEditsFocusListener);
        lastNameEdit.setOnFocusChangeListener(onNameEditsFocusListener);
    }

    @Override
    public void onContactUpdatedSuccessfully() {
        Navigation.findNavController(view).navigate(R.id.contactsListFragment);
    }

    @Override
    public void onContactUpdateFail(Throwable throwable) {
        Toast.makeText(getContext(), getString(R.string.unableToUpdateContact), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onContactValidationFailed(Map<String, String> errors) {
        contactValidationFailed(errors);
    }

    @Override
    public void onUpdateIconSuccess(ContactIconColor color) {
        displayContactIcon(color);
    }

    @Override
    public void onUpdateIconFail(Throwable throwable) {
        Toast.makeText(getContext(), getString(R.string.unableToUpdateContactIcon), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onContactIconClear() {
        clearContactIcon();
    }

    private void showContact(Contact contact) {
        firstNameEdit.setText(contact.getFirstName());
        lastNameEdit.setText(contact.getLastName());
        emailEdit.setText(contact.getEmail());
        phoneEdit.setText(contact.getPhone());
        homeAddressEdit.setText(contact.getHomeAddress());
        Date dateOfBirth = contact.getDateOfBirth();
        if (dateOfBirth != null)
            dateOfBirthEdit.setText(DateFormatter.toString(dateOfBirth));
        presenter.updateContactIcon(contact.getFirstChars());
    }
}
