package models;

public class Armor extends Item {
    private final int resistance;

    public Armor(String name, double weight, int resistance) {
        super(name, weight, false);
        this.resistance = resistance;
    }

    public int getResistance() {
        return this.resistance;
    }
}
