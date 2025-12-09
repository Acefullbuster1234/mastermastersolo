package models;

public class Consumable extends Item {
    private final boolean stackable;
    private int quantity;

    public Consumable(String name, double weight, boolean stackable) {
        super(name, weight, true);
        this.stackable = stackable;
    }

    public boolean isStackable() {
        return this.stackable;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public int getRequiredSlots() {
        return !this.stackable ? 1 : (int)Math.ceil((double)this.quantity / (double)5.0F);
    }
}
