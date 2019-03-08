package com.maxdev.maxphonebook.contacts.add;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.maxdev.maxphonebook.R;
import com.maxdev.maxphonebook.contacts.Contact;
import com.maxdev.maxphonebook.contacts.list.ContactsListFragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class ContactAddFragment extends Fragment {
    EditText firstNameEdit;
    EditText lastNameEdit;
    EditText phoneEdit;
    EditText emailEdit;

    public ContactAddFragment() {
        // Required empty public constructor
    }

    public static ContactAddFragment newInstance(String param1, String param2) {
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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_contact_add, container, false);
        firstNameEdit = (EditText) view.findViewById(R.id.first_name_edit);
        lastNameEdit = (EditText) view.findViewById(R.id.last_name_edit);
        phoneEdit = (EditText) view.findViewById(R.id.phone_number_edit);
        emailEdit = (EditText) view.findViewById(R.id.email_edit);
        Button addContactButton = (Button) view.findViewById(R.id.add_contact_button);
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContactCreate();
            }
        });
        return view;
    }

    private void onContactCreate() {
        String firstName = firstNameEdit.getText().toString();
        String lastName = lastNameEdit.getText().toString();
        String phone = phoneEdit.getText().toString();
        String email = emailEdit.getText().toString();
        Contact contact = new Contact(firstName, lastName, email, phone);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentLayout, new ContactsListFragment());
        fragmentTransaction.commit();
    }
}
