package models;

public class Consumable extends Item {

    public Consumable(String name, double weight) {
        super(name, weight, true, ItemType.Consumable);
    }
}
