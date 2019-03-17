package com.maxdev.maxphonebook.contacts.add;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maxdev.maxphonebook.R;
import com.maxdev.maxphonebook.db.contacticoncolors.ContactIconColor;
import com.maxdev.maxphonebook.db.contacts.Contact;
import com.maxdev.maxphonebook.db.contacts.ContactsValidator;
import com.maxdev.maxphonebook.utils.DateFormatter;

import org.w3c.dom.Text;

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
    TextView contactIconPreviewView;
    Button addContactButton;

    ContactsAddPresenter presenter;
    private View view;
    private View.OnFocusChangeListener onLastNameFocusChange =
            new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String firstName = firstNameEdit.getText().toString();
                        String lastName = lastNameEdit.getText().toString();
                        String chars = String.format("%c%c",
                                firstName.charAt(0), lastName.charAt(0)).toUpperCase();
                        presenter.selectColor(chars);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void locateElements() {
        firstNameEdit = (EditText) view.findViewById(R.id.firstNameEdit);
        lastNameEdit = (EditText) view.findViewById(R.id.lastNameEdit);
        phoneEdit = (EditText) view.findViewById(R.id.phoneNumberEdit);
        emailEdit = (EditText) view.findViewById(R.id.emailEdit);
        dateOfBirthEdit = (EditText) view.findViewById(R.id.dateOfBirthEdit);
        homeAddressEdit = (EditText) view.findViewById(R.id.homeAddressEdit);
        addContactButton = (Button) view.findViewById(R.id.add_contact_button);
        contactIconPreviewView = (TextView) view.findViewById(R.id.contactIconPreviewView);

        addContactButton.setOnClickListener(onAddButtonClickListener);
        lastNameEdit.setOnFocusChangeListener(onLastNameFocusChange);
        lastNameEdit.setFocusable(true);
    }

    private Drawable getIconBackground(int color, int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        return drawable;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact_add, container, false);
        locateElements();
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
            Date dateOfBirth;
            if (!dateOfBirthEdit.getText().toString().equals(""))
                dateOfBirth = DateFormatter.fromString(dateOfBirthEdit.toString());
            Contact contact = new Contact(firstName, lastName, email, phone, homeAddress, null);
            presenter.saveContact(contact);
        } catch (ParseException ex) {
            Toast.makeText(getContext(), getString(R.string.invalidDateFormat), Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onContactSavedSuccessfully() {
        Navigation.findNavController(view).navigate(R.id.contactsListFragment);
    }

    @Override
    public void onContactValidationFailed() {
        Toast.makeText(getContext(), getString(R.string.invalidNameError), Toast.LENGTH_SHORT);
    }

    @Override
    public void onContactSaveFailed(Throwable throwable) {
        Toast.makeText(getContext(), getString(R.string.save_contact_failed), Toast.LENGTH_SHORT);
    }

    @Override
    public void displayContactIcon(ContactIconColor color) {
        contactIconPreviewView.setText(color.getStartChars());
        Drawable background = getIconBackground(color.getColor(), contactIconPreviewView.getWidth());
        contactIconPreviewView.setBackground(background);
    }

    @Override
    public void clearContactIcon() {
        contactIconPreviewView.setText("");
        contactIconPreviewView.setBackground(null);
    }
}
