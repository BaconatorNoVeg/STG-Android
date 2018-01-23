package com.baconatornoveg.stg.engine;

public class Item {

    private String name;

    public boolean isPhysical;
    public boolean isMagical;

    public Item(boolean isPhysical, boolean isMagical, String name) {
        this.name = name;
        this.isPhysical = isPhysical;
        this.isMagical = isMagical;
    }

    public String toString() {
        return this.name;
    }

}
