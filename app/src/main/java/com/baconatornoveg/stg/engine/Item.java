package com.baconatornoveg.stg.engine;

public class Item {

    private String name;
    private String itemType;
    private boolean isPhysical;
    private boolean isMagical;


    Item(boolean isPhysical, boolean isMagical, String itemType, String name) {
        this.name = name;
        this.itemType = itemType;
        this.isPhysical = isPhysical;
        this.isMagical = isMagical;
    }

    public boolean isPhysical() {
        return isPhysical;
    }

    public boolean isMagical() {
        return isMagical;
    }

    public boolean isOffensive() { return itemType != null && (itemType.equals("OFFENSE") || itemType.equals("BOTH")); }

    public boolean isDefensive() { return itemType != null && (itemType.equals("DEFENSE") || itemType.equals("BOTH")); }

    public String toString() {
        return this.name;
    }

}
