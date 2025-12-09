package servicelogic;

import exceptions.ItemNotFound;
import exceptions.MaxSlotsReached;
import exceptions.MaxWeightReached;
import exceptions.PlayerNameProblem;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import models.Armor;
import models.Consumable;
import models.Inventory;
import models.Item;
import models.Player;
import models.Weapon;

public class InventoryService {
    private final List<Item> worldItems = new ArrayList();
    private final List<Player> players = new ArrayList();

    public InventoryService() {
        this.defaultItems();
        this.defaultPlayer();
    }

    public Player createPlayer(String name) throws PlayerNameProblem {
        if (this.getPlayerByName(name) != null) {
            throw new PlayerNameProblem(name + " already exist");
        } else {
            Player p = new Player(name);
            this.players.add(p);
            return p;
        }
    }

    public Player loginPlayer(String name) throws PlayerNameProblem {
        if (this.getPlayerByName(name) == null) {
            throw new PlayerNameProblem(name + " doesn't exist");
        } else {
            return this.getPlayerByName(name);
        }
    }

    private Player getPlayerByName(String name) {
        for(Player p : this.players) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }

        return null;
    }

    public void addItem(Player p, String itemName) throws ItemNotFound, MaxWeightReached, MaxSlotsReached {
        Item findItem = this.getWorldItemByName(itemName);
        if (findItem == null) {
            throw new ItemNotFound(itemName);
        } else {
            this.checkInventory(p, findItem);
            p.getInventory().addItem(findItem);
        }
    }

    public void removeItem(Player p, String itemName) throws ItemNotFound {
        Item findItem = this.getWorldItemByName(itemName);
        if (findItem == null) {
            throw new ItemNotFound(itemName + " not found");
        } else {
            p.getInventory().removeItem(findItem);
        }
    }

    private Item getWorldItemByName(String name) {
        for(Item i : this.worldItems) {
            if (i.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }

        return null;
    }

    public void checkInventory(Player p, Item item) throws MaxWeightReached, MaxSlotsReached {
        int availablePlayerSlots = p.getInventory().getInventorySlots().getCurrentSlots();
        int usedPlayerSlots = p.getInventory().getInventorySlots().getUsedSlots();
        double usedPlayerWeight = p.getInventory().getCurrentPlayerCarryWeight() + item.getWeight();
        double maxPlayerWeight = p.getInventory().getMaxPlayerCarryWeight();
        if (usedPlayerSlots >= availablePlayerSlots) {
            throw new MaxSlotsReached(usedPlayerSlots + " out of " + availablePlayerSlots + " slots filled");
        } else if (usedPlayerWeight >= maxPlayerWeight) {
            throw new MaxWeightReached("Weight limit reached.");
        }
    }

    public void increasePlayerMaxSlots(Player p) {
        p.getInventory().getInventorySlots().setCurrentMaxSlots();
    }

    public void defaultItems() {
        this.worldItems.add(new Weapon("Short Sword", (double)2.5F, 10));
        this.worldItems.add(new Weapon("Short Axe", (double)2.5F, 10));
        this.worldItems.add(new Armor("Helmet", (double)2.0F, 2));
        this.worldItems.add(new Armor("Chestplate", (double)8.0F, 6));
        this.worldItems.add(new Consumable("Health Potion", 0.2, true));
        this.worldItems.add(new Weapon("Test Sword", 49.9999, 10));
    }

    public void defaultPlayer() {
        this.players.add(new Player("Test"));
    }

    public void displayWorldItems() {
        System.out.println("--- Available items ---");

        for(Item i : this.worldItems) {
            PrintStream var10000 = System.out;
            String var10001 = i.getName();
            var10000.println("- " + var10001 + " (" + i.getWeight() + " kg)");
        }

    }

    public void displayPlayers() {
        System.out.println("--- Players ---");

        for(Player p : this.players) {
            System.out.println("- " + p.getName());
        }

    }

    public void displayPlayerInventory(Player p) {
        Inventory inv = p.getInventory();
        List<Inventory.Slot> slots = inv.getSlots();
        if (slots.isEmpty()) {
            System.out.println("Your inventory is empty");
        } else {
            System.out.println("-----------------");

            for(Inventory.Slot slot : slots) {
                List<Item> itemsInSlot = slot.getItems();
                if (!itemsInSlot.isEmpty()) {
                    Item firstItem = (Item)itemsInSlot.get(0);
                    String itemName = firstItem.getName();
                    double itemWeight = firstItem.getWeight();
                    int count = itemsInSlot.size();
                    if (firstItem.isStackable()) {
                        System.out.println("- " + itemName + " x" + count + " (" + itemWeight * (double)count + " kg)");
                    } else {
                        for(Item item : itemsInSlot) {
                            PrintStream var10000 = System.out;
                            String var10001 = item.getName();
                            var10000.println("- " + var10001 + " (" + item.getWeight() + " kg)");
                        }
                    }
                }
            }

            System.out.println("---------------");
        }
    }
}
