package models;

import exceptions.MaxSlotsReached;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final double maxPlayerCarryWeight = (double)50.0F;
    private double currentPlayerCarryWeight = (double)0.0F;
    private final Slots inventorySlots = new Slots();
    private final List<Slot> slots = new ArrayList();

    public double getMaxPlayerCarryWeight() {
        return (double)50.0F;
    }

    public double getCurrentPlayerCarryWeight() {
        return this.currentPlayerCarryWeight;
    }

    public Slots getInventorySlots() {
        return this.inventorySlots;
    }

    public List<Slot> getSlots() {
        return this.slots;
    }

    public void addItem(Item item) throws MaxSlotsReached {
        if (item.isStackable() && item instanceof Consumable) {
            for(Slot s : this.slots) {
                if (s.canAdd(item)) {
                    s.add(item);
                    this.recalculateWeightAndSlots();
                    return;
                }
            }
        }

        if (this.inventorySlots.getUsedSlots() >= this.inventorySlots.getCurrentSlots()) {
            throw new MaxSlotsReached("No free inventory slots!");
        } else {
            Slot newSlot = new Slot();
            newSlot.add(item);
            this.slots.add(newSlot);
            this.recalculateWeightAndSlots();
        }
    }

    public void removeItem(Item item) {
        for(Slot s : this.slots) {
            if (s.getItems().contains(item)) {
                s.remove(item);
                if (s.getItems().isEmpty()) {
                    this.slots.remove(s);
                }
                break;
            }
        }

        this.recalculateWeightAndSlots();
    }

    private void recalculateWeightAndSlots() {
        double totalWeight = (double)0.0F;
        int usedSlotsCount = 0;

        for(Slot s : this.slots) {
            for(Item i : s.getItems()) {
                totalWeight += i.getWeight();
            }

            ++usedSlotsCount;
        }

        this.currentPlayerCarryWeight = totalWeight;
        this.inventorySlots.setUsedSlots(usedSlotsCount);
    }

    public static class Slot {
        private final List<Item> items = new ArrayList();
        private final int MAX_STACK = 5;

        public List<Item> getItems() {
            return this.items;
        }

        public boolean canAdd(Item item) {
            if (this.items.isEmpty()) {
                return true;
            } else {
                Item first = (Item)this.items.get(0);
                return first.isStackable() && item.getClass() == first.getClass() && this.items.size() < 5;
            }
        }

        public void add(Item item) {
            if (!this.canAdd(item)) {
                throw new IllegalStateException("Cannot add item to this slot");
            } else {
                this.items.add(item);
            }
        }

        public void remove(Item item) {
            this.items.remove(item);
        }
    }
}
