package main;

import java.util.ArrayList;

public class Player {
    String name;
    int health;
    ArrayList<Item> inventory;

    // NEW status flags
    boolean skipTurn = false;
    boolean hasMorphine = false;
    boolean hasKnife = false;

    public Player(String name) {
        this.name = name;
        this.health = 3;
        this.inventory = new ArrayList<Item>();
    }

    public void addItem(Item item) {
        inventory.add(item);
        System.out.println(name + " received: " + item.name);
    }

    public void showInventory() {
        System.out.println(name + "'s Inventory:");
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            System.out.println((i + 1) + ". " + item.name + " - " + item.description);
        }
    }

    public void useItem(int index, Player opponent) {
        if (index >= 0 && index < inventory.size()) {
            Item item = inventory.remove(index);
            item.use(this, opponent);
        }
    }

    public void takeDamage(int amount) {
        if (hasMorphine) {
            System.out.println(name + " blocked the damage with Morphine!");
            hasMorphine = false;
        } else {
            health -= amount;
            System.out.println(name + " took " + amount + " damage.");
        }
    }

    public void heal(int amount) {
        health += amount;
        if (health > 3) {
            health = 3;
        }
        System.out.println(name + " now has " + health + " HP.");
    }

    public boolean isAlive() {
        return health > 0;
    }
}
