package models;

import exceptions.MaxSlotsReached;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private final double maxPlayerCarryWeight = 50.00;
    private double currentPlayerCarryWeight = 0;

    private final Slots inventorySlots = new Slots();
    private final List<Slot> slots = new ArrayList<>();

    public Inventory() {}

    public double getMaxPlayerCarryWeight() {
        return maxPlayerCarryWeight;
    }

    public double getCurrentPlayerCarryWeight() {
        return currentPlayerCarryWeight;
    }

    public Slots getInventorySlots() {
        return inventorySlots;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    // Add an item to inventory
    public void addItem(Item item) throws MaxSlotsReached {
        if (item.isStackable() && item.getType() == ItemType.Armor) {
            // Try to add to an existing stack
            for (Slot s : slots) {
                if (s.canAdd(item)) {
                    s.add(item);
                    recalculateWeightAndSlots();
                    return;
                }
            }
        }

        // Otherwise, create a new slot
        if (inventorySlots.getUsedSlots() >= inventorySlots.getCurrentSlots()) {
            throw new MaxSlotsReached("No free inventory slots!");
        }

        Slot newSlot = new Slot();
        newSlot.add(item);
        slots.add(newSlot);
        recalculateWeightAndSlots();
    }

    // Remove an item from inventory
    public void removeItem(Item item) {
        for (Slot s : slots) {
            if (s.getItems().contains(item)) {
                s.remove(item);
                // Remove empty slots
                if (s.getItems().isEmpty()) {
                    slots.remove(s);
                }
                break;
            }
        }
        recalculateWeightAndSlots();
    }

    // Recalculate weight and used slots
    private void recalculateWeightAndSlots() {
        double totalWeight = 0;
        int usedSlotsCount = 0;

        for (Slot s : slots) {
            for (Item i : s.getItems()) {
                totalWeight += i.getWeight();
            }
            usedSlotsCount += 1; // 1 slot per Slot object
        }

        currentPlayerCarryWeight = totalWeight;
        inventorySlots.setUsedSlots(usedSlotsCount);
    }

    // Inner class representing a slot
    public static class Slot {
        private final List<Item> items = new ArrayList<>();
        private final int MAX_STACK = 5;

        public List<Item> getItems() {
            return items;
        }

        public boolean canAdd(Item item) {
            if (items.isEmpty()) return true;
            Item first = items.get(0);
            return first.isStackable()
                    && item.getClass() == first.getClass()
                    && items.size() < MAX_STACK;
        }

        public void add(Item item) {
            if (!canAdd(item)) {
                throw new IllegalStateException("Cannot add item to this slot");
            }
            items.add(item);
        }

        public void remove(Item item) {
            items.remove(item);
        }
    }
}
