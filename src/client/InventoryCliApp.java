package client;

import exceptions.ItemNotFound;
import exceptions.MaxSlotsReached;
import exceptions.MaxWeightReached;
import exceptions.PlayerNameProblem;
import java.util.Scanner;
import models.Player;
import servicelogic.InventoryService;

public class InventoryCliApp {
    private final InventoryService inv = new InventoryService();
    private Player player;

    public static void main(String[] args) {
        (new InventoryCliApp()).mainMenu();
    }

    private void mainMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while(running) {
                printMenu();
                switch (scanner.nextLine()) {
                    case "1":
                        this.handlePlayerCreating(scanner);
                        break;
                    case "2":
                        this.handleLogInPlayer(scanner);
                        break;
                    case "3":
                        running = false;
                        System.out.println("Shutting down");
                }
            }
        }

    }

    private static void printMenu() {
        System.out.println("---Main menu---");
        System.out.println("1. Create new player");
        System.out.println("2. Log in");
        System.out.println("3. Exit");
        System.out.println("------");
        System.out.print("Choice: ");
    }

    private void handlePlayerCreating(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        try {
            this.player = this.inv.createPlayer(username);
            System.out.println("Player " + username + " has been created");
            this.gameMenu(scanner);
        } catch (PlayerNameProblem e) {
            System.out.println(e.getMessage());
        }

    }

    private void handleLogInPlayer(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        try {
            this.player = this.inv.loginPlayer(username);
            this.gameMenu(scanner);
        } catch (PlayerNameProblem e) {
            System.out.println(e.getMessage());
        }

    }

    public void gameMenu(Scanner scanner) {
        boolean running = true;

        while(running) {
            printGameMenu();
            switch (scanner.nextLine()) {
                case "1":
                    this.inventoryMenu(scanner);
                    break;
                case "2":
                    this.handleDisplayWorldItems();
                    break;
                case "3":
                    running = false;
                    System.out.println("Returning");
            }
        }

    }

    private static void printGameMenu() {
        System.out.println("---Game Menu---");
        System.out.println("1. See inventory");
        System.out.println("2. See all items");
        System.out.println("3. Exit");
        System.out.println("------");
        System.out.print("Choice: ");
    }

    private void handleDisplayWorldItems() {
        this.inv.displayWorldItems();
    }

    public void inventoryMenu(Scanner scanner) {
        boolean running = true;

        while(running) {
            this.inv.displayPlayerInventory(this.player);
            if (this.player != null && this.player.getInventory() != null) {
                System.out.println("Used Slots; " + this.player.getInventory().getInventorySlots().getUsedSlots());
            }

            this.printInventoryMenu();
            switch (scanner.nextLine()) {
                case "1":
                    this.handleAddItem(scanner);
                    break;
                case "2":
                    this.handleRemoveItem(scanner);
                    break;
                case "3":
                    running = false;
                    System.out.println("Returning");
            }
        }

    }

    public void printInventoryMenu() {
        System.out.println("---Inventory Menu---");
        System.out.println("1. Add item");
        System.out.println("2. Remove item");
        System.out.println("3. Exit");
        System.out.println("------");
        System.out.print("Choice: ");
    }

    public void handleAddItem(Scanner scanner) {
        System.out.print("Item to add: ");
        String choice = scanner.nextLine();

        try {
            this.inv.addItem(this.player, choice);
        } catch (MaxWeightReached e) {
            System.out.println(e.getMessage());
        } catch (MaxSlotsReached e) {
            System.out.println(e.getMessage());
        } catch (ItemNotFound e) {
            System.out.println(e.getMessage());
        }

    }

    public void handleRemoveItem(Scanner scanner) {
        System.out.print("Item to remove: ");
        String choice = scanner.nextLine();

        try {
            this.inv.removeItem(this.player, choice);
            System.out.println(choice + " has been removed from your inventory");
        } catch (ItemNotFound e) {
            System.out.println(e.getMessage());
        }

    }
}
