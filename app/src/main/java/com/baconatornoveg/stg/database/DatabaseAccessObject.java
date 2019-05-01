package com.baconatornoveg.stg.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DatabaseAccessObject {
    @Transaction
    @Query("select * from players")
    List<DBPlayer> loadAll();

    @Query("select * from players where id in (:id)")
    DBPlayer getBuild(int id);

    @Insert
    void insertAll(DBPlayer dbPlayer);

    @Delete
    void delete(DBPlayer dbPlayer);

    @Query("select * from players order by id desc limit 1")
    DBPlayer getLast();

    @Update
    void update(DBPlayer dbPlayer);
}
