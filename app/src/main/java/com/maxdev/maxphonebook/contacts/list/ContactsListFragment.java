package com.maxdev.maxphonebook.contacts.list;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maxdev.maxphonebook.R;
import com.maxdev.maxphonebook.contacts.Contact;
import com.maxdev.maxphonebook.contacts.ContactsRepository;
import com.maxdev.maxphonebook.contacts.add.ContactAddFragment;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class ContactsListFragment extends Fragment {
    private ContactsListPresenter presenter;

    public ContactsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ContactsListPresenter(getFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);
        FloatingActionButton contactAddButton = view.findViewById(R.id.addContactButton);
        contactAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                ContactAddFragment fragment = new ContactAddFragment();
                fragmentTransaction.replace(R.id.fragmentLayout, fragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
