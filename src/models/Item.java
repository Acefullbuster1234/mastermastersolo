package models;

public abstract class Item {
    private final String name;
    private final double weight;
    private final boolean isStackable;

    public Item(String name, double weight, boolean isStackerble) {
        this.name = name;
        this.weight = weight;
        this.isStackable = isStackerble;
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
}
