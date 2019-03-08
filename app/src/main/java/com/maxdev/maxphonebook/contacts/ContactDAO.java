package com.maxdev.maxphonebook.contacts;



import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ContactDAO {
    @Insert(onConflict = REPLACE)
    Completable save(Contact contact);
    @Query("SELECT * FROM Contacts LIMIT :contactsPage")
    List<Contact> getPage(int contactsPage);
    @Query("SELECT * FROM Contacts WHERE id = :id")
    Contact getContact(int id);
    @Delete
    int deleteContact(Contact contact);
    @Update
    int updateContact(Contact contact);
}
