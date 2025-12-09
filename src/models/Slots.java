package models;

import exceptions.MaxSlotsReached;

public class Slots {
    private final int maxSlots = 192;
    private int currentSlots = 32;
    private int usedSlots = 0;

    public int getCurrentSlots() {
        return this.currentSlots;
    }

    public int getUsedSlots() {
        return this.usedSlots;
    }

    public void setUsedSlots(int amount) {
        this.usedSlots = amount;
    }

    public void setCurrentMaxSlots() throws MaxSlotsReached {
        if (this.currentSlots >= 192) {
            throw new MaxSlotsReached("You have unlocked the max amount of inventory space");
        } else {
            this.currentSlots += 32;
        }
    }
}
