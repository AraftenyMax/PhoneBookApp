package com.maxdev.maxphonebook.contacts.list;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maxdev.maxphonebook.R;
import com.maxdev.maxphonebook.db.contacts.Contact;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ContactsListFragment extends Fragment implements ContactsListPresenter.View {
    private List<Contact> contacts;
    private ContactsListPresenter presenter;
    private RecyclerView contactsView;
    private ContactsListAdapter adapter;
    public final static String contactIdKey = "ContactId";

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int id = viewHolder.getAdapterPosition();
            Contact selectedContact = contacts.get(id);
            ContactsListFragmentDirections.ContactfromListToDetailed action =
                    ContactsListFragmentDirections.ContactfromListToDetailed(selectedContact);
            action.setContact(selectedContact);
            Navigation.findNavController(v).navigate(action);
        }
    };

    public ContactsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ContactsListPresenter(this);
    }

    public static ContactsListFragment getNewInstance() {
        return new ContactsListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);
        contactsView = view.findViewById(R.id.contactsView);
        FloatingActionButton contactAddButton = view.findViewById(R.id.addContactButton);
        contactAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.contactAddFragment);
            }
        });
        return view;
    }

    private void setUpRecycleView(List<Contact> contactsList) {
        this.contacts = contactsList;
        adapter = new ContactsListAdapter(contactsList);
        adapter.setOnItemClickListener(onItemClickListener);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        contactsView.setLayoutManager(layoutManager);
        contactsView.setItemAnimator(new DefaultItemAnimator());
        contactsView.setAdapter(adapter);
    }

    @Override
    public void onContactsListFetched(List<Contact> contactList) {
        setUpRecycleView(contactList);
    }
}
