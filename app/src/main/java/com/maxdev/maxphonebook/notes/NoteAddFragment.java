package com.maxdev.maxphonebook.notes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxdev.maxphonebook.R;

import androidx.fragment.app.Fragment;

public class NoteAddFragment extends Fragment {

    public NoteAddFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static NoteAddFragment newInstance(String param1, String param2) {
        NoteAddFragment fragment = new NoteAddFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_add, container, false);
    }
}
