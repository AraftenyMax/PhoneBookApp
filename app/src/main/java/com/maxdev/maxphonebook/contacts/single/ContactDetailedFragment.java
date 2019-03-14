package com.maxdev.maxphonebook.contacts.single;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maxdev.maxphonebook.R;
import com.maxdev.maxphonebook.db.contacts.Contact;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactDetailedFragment extends Fragment implements ContactDetailedPresenter.View {
    private int contactId;
    private View view;
    private Contact contact;
    private ContactDetailedPresenter presenter;
    private TextView firstName;
    private TextView lastName;
    private TextView phone;
    private TextView email;
    private Button deleteButton;
    private FloatingActionButton editButton;

    private View.OnClickListener onDeleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.deleteContact(contact);
        }
    };

    private View.OnClickListener onUpdateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContactDetailedFragmentDirections.ActionContactDetailToEdit action =
                    ContactDetailedFragmentDirections.actionContactDetailToEdit(contact);
            action.setContact(contact);
            Navigation.findNavController(v).navigate(action);
        }
    };

    public ContactDetailedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ContactDetailedPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contactId = ContactDetailedFragmentArgs.fromBundle(getArguments()).getContactId();
        view = inflater.inflate(R.layout.fragment_contact_detailed, container, false);
        locateElements();
        presenter.loadContact(contactId);
        return view;
    }

    private void locateElements() {
        firstName = (TextView)view.findViewById(R.id.firstNameDetailView);
        lastName = (TextView)view.findViewById(R.id.lastNameDetailView);
        phone = (TextView)view.findViewById(R.id.phoneDetailView);
        email = (TextView)view.findViewById(R.id.emailDetailView);
        deleteButton = (Button)view.findViewById(R.id.deleteContactButton);
        deleteButton.setOnClickListener(onDeleteClickListener);
        editButton = (FloatingActionButton)view.findViewById(R.id.editFloatingButton);
        editButton.setOnClickListener(onUpdateClickListener);
    }

    @Override
    public void onContactLoaded(Contact contact) {
        this.contact = contact;
        firstName.setText(contact.getFirstName());
        lastName.setText(contact.getLastName());
        phone.setText(contact.getPhone());
        email.setText(contact.getEmail());
    }

    @Override
    public void onContactDeleteSuccess() {
        Navigation.findNavController(view).navigate(R.id.contactsListFragment);
    }
}
