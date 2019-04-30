package com.baconatornoveg.stg.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = DBPlayer.class, version = 1)
public abstract class BuildDatabase extends RoomDatabase {
    public abstract DatabaseAccessObject dao();
}
