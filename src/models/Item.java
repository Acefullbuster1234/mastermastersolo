package models;

public abstract class Item {
    private final String name;
    private final double weight;
    private final boolean isStackable;
    private final ItemType type;

    public Item(String name, double weight, boolean isStackerble, ItemType type) {
        this.name = name;
        this.weight = weight;
        this.isStackable = isStackerble;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        return this.weight;
    }

    public boolean isStackable() {
        return this.isStackable;
    }
    public ItemType getType() {
        return this.type;
    }
}
