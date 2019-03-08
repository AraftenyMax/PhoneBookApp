package com.maxdev.maxphonebook;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.maxdev.maxphonebook.contacts.list.ContactsListFragment;
import com.maxdev.maxphonebook.events.EventsListFragment;
import com.maxdev.maxphonebook.notes.NotesListFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private int currentItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentLayout, new ContactsListFragment());
        ft.commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                if (currentItem == menuItem.getItemId())
                    return false;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.nav_contacts:
                        ft.replace(R.id.fragmentLayout, new ContactsListFragment());
                        break;
                    case R.id.nav_events:
                        ft.replace(R.id.fragmentLayout, new EventsListFragment());
                        break;
                    case R.id.nav_notes:
                        ft.replace(R.id.fragmentLayout, new NotesListFragment());
                        break;
                }
                ft.commit();
                currentItem = menuItem.getItemId();
                return true;
            }
        });
    }
}
