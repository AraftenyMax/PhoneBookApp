package com.maxdev.maxphonebook.db.contacts;



import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ContactDAO {
    @Insert(onConflict = REPLACE)
    Completable save(Contact contact);
    @Query("SELECT * FROM Contacts")
    Flowable<List<Contact>> getPage();
    @Query("SELECT * FROM Contacts WHERE id = :id")
    Single<Contact> getContact(int id);
    @Delete
    Completable deleteContact(Contact contact);
    @Update
    Completable updateContact(Contact contact);
}
