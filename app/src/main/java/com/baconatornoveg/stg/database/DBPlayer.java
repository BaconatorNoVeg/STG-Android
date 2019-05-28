package com.baconatornoveg.stg.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "players")
public class DBPlayer {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "build_name")
    private String buildName;
    @ColumnInfo(name = "god")
    private String god;
    @ColumnInfo(name = "build")
    private String build;
    @ColumnInfo(name = "relics")
    private String relics;

    public DBPlayer(int id, String buildName, String god, String build, String relics) {
        this.id = id;
        this.buildName = buildName;
        this.god = god;
        this.build = build;
        this.relics = relics;
    }

    public int getId() {
        return id;
    }

    public String getBuildName() {
        return buildName;
    }

    public String getGod() {
        return god;
    }

    public String getBuild() {
        return build;
    }

    public String getRelics() {
        return relics;
    }

    public void setBuildName(String name) {
        this.buildName = name;
    }
}
