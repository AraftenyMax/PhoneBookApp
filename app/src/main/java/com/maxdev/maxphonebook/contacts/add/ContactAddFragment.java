package com.maxdev.maxphonebook.contacts.add;

import android.content.Context;
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

import java.text.ParseException;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;


public class ContactAddFragment extends ContactEditBaseFragment implements ContactsAddPresenter.View {
    private final static String TAG = "ContactAddFragment";
    private Button addContactButton;
    ContactsAddPresenter presenter;
    private View.OnFocusChangeListener onNameEditListener =
            new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        try {
                            String nameInitials = getNameInitials();
                            presenter.selectColor(nameInitials);
                        } catch (IndexOutOfBoundsException ex) {
                            onClearContactIcon();
                        }
                    }
                }
            };
    private View.OnClickListener onAddButtonClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onContactSave();
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
    protected void locateElements() {
        super.locateElements();
        addContactButton = (Button) view.findViewById(R.id.addContactButtonDetailed);
        addContactButton.setOnClickListener(onAddButtonClickListener);
        firstNameEdit.setOnFocusChangeListener(onNameEditListener);
        lastNameEdit.setOnFocusChangeListener(onNameEditListener);
        lastNameEdit.setFocusable(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_add, container, false);
        super.locateElements();
        locateElements();
        return view;
    }

    @Override
    public void onContactSave() {
        try {
            Contact contact = getContact();
            presenter.saveContact(contact);
        } catch (ParseException ex) {
            Log.e(TAG, ex.getMessage());
            Toast.makeText(getContext(),
                    getString(R.string.invalidDateFormat), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onContactSavedSuccessfully() {
        Navigation.findNavController(view).navigate(R.id.contactsListFragment);
    }

    @Override
    public void onContactValidationFailed(Map<String, String> errors) {
        contactValidationFailed(errors);
    }

    @Override
    public void onContactSaveFailed(Throwable throwable) {
        Toast.makeText(getContext(), getString(R.string.save_contact_failed), Toast.LENGTH_SHORT);
    }

    @Override
    public void onDisplayContactIcon(ContactIconColor color) {
        displayContactIcon(color);
    }

    @Override
    public void onClearContactIcon() {
        clearContactIcon();
    }
}
