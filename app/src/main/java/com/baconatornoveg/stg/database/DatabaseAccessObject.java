package com.baconatornoveg.stg.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DatabaseAccessObject {
    @Query("SELECT * FROM DBPlayer")
    List<DBPlayer> loadAll();

    @Insert
    void insertAll(DBPlayer dbPlayer);

    @Delete
    void delete(DBPlayer dbPlayer);
}
