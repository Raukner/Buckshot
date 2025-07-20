package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    Player player;
    Player boss;
    Scanner scanner;
    Random random;
    int currentRound;

    public Game() {
        scanner = new Scanner(System.in);
        random = new Random();
        currentRound = 1;

        player = new Player("You");
        boss = new Player("Boss");

        // Start with a random item each
        player.addItem(getRandomItem());
        boss.addItem(getRandomItem());
    }

    public void start() {
        System.out.println("=== Buckshot Roulette ===");
        System.out.println("Best of 3 rounds. First to win 2 rounds is the champion.");

        int playerWins = 0;
        int bossWins = 0;

        while (currentRound <= 3) {
            System.out.println("\n--- ROUND " + currentRound + " ---");

            player.health = 3;
            boss.health = 3;

            player.inventory.clear();
            boss.inventory.clear();

            player.addItem(getRandomItem());
            boss.addItem(getRandomItem());

            playRound();

            if (player.isAlive()) {
                System.out.println("You win this round!");
                playerWins++;
            } else {
                System.out.println("Boss wins this round.");
                bossWins++;
            }

            currentRound++;
        }

        System.out.println("\n=== GAME OVER ===");
        if (playerWins > bossWins) {
            System.out.println("YOU WON THE GAME!");
        } else {
            System.out.println("THE BOSS WON THE GAME!");
        }
    }

    public void playRound() {
        boolean isPlayerTurn = true;

        while (player.isAlive() && boss.isAlive()) {
            if (isPlayerTurn) {
                playerTurn(player, boss);
            } else {
                playerTurn(boss, player);
            }
            isPlayerTurn = !isPlayerTurn;
        }
    }

    public void playerTurn(Player current, Player opponent) {
        System.out.println("\n" + current.name + "'s turn:");
        System.out.println(current.name + " HP: " + current.health);

        // Check if turn should be skipped
        if (current.skipTurn) {
            System.out.println(current.name + "'s turn is skipped!");
            current.skipTurn = false;
            return;
        }

        current.showInventory();

        System.out.println("Choose an action:");
        System.out.println("1. Use item");
        System.out.println("2. Pick up gun and shoot");

        int choice = 0;
        if (current.name.equals("You")) {
            choice = scanner.nextInt();
            scanner.nextLine(); // clear newline
        } else {
            choice = random.nextInt(2) + 1;
        }

        if (choice == 1 && current.inventory.size() > 0) {
            current.useItem(0, opponent);
        } else {
            boolean bullet = random.nextBoolean();
            if (bullet) {
                int damage = current.hasKnife ? 2 : 1;
                System.out.println("BANG! The gun fired a bullet for " + damage + " damage!");
                opponent.takeDamage(damage);
                current.hasKnife = false; // reset knife effect
            } else {
                System.out.println("CLICK. Empty chamber.");
            }
        }

        System.out.println("Player HP: " + player.health + " | Boss HP: " + boss.health);
    }

    public Item getRandomItem() {
        int roll = random.nextInt(4);
        switch (roll) {
            case 0:
                return new Item("Bandage", "Heals 1 HP");
            case 1:
                return new Item("Knife", "Deals double damage");
            case 2:
                return new Item("Handcuffs", "Skip opponent's turn");
            case 3:
                return new Item("Morphine", "Negates next damage");
            default:
                return new Item("Bandage", "Heals 1 HP");
        }
    }
}
