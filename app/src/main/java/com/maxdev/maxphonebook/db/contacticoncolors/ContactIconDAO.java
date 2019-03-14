package com.maxdev.maxphonebook.db.contacticoncolors;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface ContactIconDAO {
    @Insert
    Completable insert(ContactIconColor color);
    @Update
    Completable update(ContactIconColor color);
    @Query("SELECT * FROM ContactIconColors WHERE startChars = :startChars")
    Single<ContactIconColor> select(String startChars);
    @Delete
    Completable delete(ContactIconColor color);
}
