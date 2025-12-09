package models;

public class Weapon extends Item {
    private int damage;

    public Weapon(String name, double weight, int damage) {
        super(name, weight, false, ItemType.Weapon);
        this.damage = damage;
    }
}
