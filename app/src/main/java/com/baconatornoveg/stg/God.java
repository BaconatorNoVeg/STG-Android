package com.baconatornoveg.stg;

public abstract class God {

    private String name;
    private String position;

    public God(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return this.name;
    }

    public String getPosition() {
        return this.position;
    }

    public String toString() {
        return this.name;
    }

}
