package com.maxdev.maxphonebook.db.contactgroup;

import com.maxdev.maxphonebook.db.contacts.Contact;

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
public interface ContactGroupDAO {
    @Query("SELECT * FROM ContactGroups WHERE name = :name")
    Single<ContactGroup> select(String name);
    @Query("SELECT * FROM ContactGroups")
    Flowable<ContactGroup> selectAll();
    @Insert(onConflict = REPLACE)
    Completable insert(ContactGroup contact);
    @Update
    Completable update(ContactGroup contact);
    @Delete
    Completable delete(ContactGroup contact);
}
