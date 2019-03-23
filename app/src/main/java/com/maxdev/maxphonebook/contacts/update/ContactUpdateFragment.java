package com.maxdev.maxphonebook.contacts.update;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.maxdev.maxphonebook.R;
import com.maxdev.maxphonebook.contacts.ContactEditBaseFragment;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconColor;
import com.maxdev.maxphonebook.db.contacts.Contact;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import androidx.navigation.Navigation;

public class ContactUpdateFragment extends ContactEditBaseFragment implements ContactUpdatePresenter.View{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "ContactUpdateFragment";
    private View view;
    private Button onUpdateContactButton;
    private ContactUpdatePresenter presenter;

    private View.OnClickListener onUpdateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Contact contact = getContact();
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

    private View.OnFocusChangeListener onNameEditsFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(!hasFocus) {
                String nameInitials = getNameInitials();
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
    @Override
    protected void locateElements() {
        super.locateElements();
        onUpdateContactButton = (Button) view.findViewById(R.id.updateContactButton);
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
}
