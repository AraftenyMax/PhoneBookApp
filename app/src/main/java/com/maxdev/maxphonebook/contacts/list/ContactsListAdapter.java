package com.maxdev.maxphonebook.contacts.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxdev.maxphonebook.R;
import com.maxdev.maxphonebook.db.contacts.Contact;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder> {
    private List<Contact> mDataset;
    private View.OnClickListener onClickListener;
    public ContactsListAdapter(List<Contact> contacts) {
        mDataset = contacts;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = mDataset.get(position);
        holder.name.setText(String.format("%s %s", contact.getLastName(), contact.getFirstName()));
        holder.phone.setText(contact.getPhone());
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        this.onClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nameView);
            phone = (TextView) itemView.findViewById(R.id.phoneView);
            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);
        }
    }
}
