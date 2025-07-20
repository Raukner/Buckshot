package main;

public class Item {
    String name;
    String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void use(Player user, Player opponent) {
        switch (name) {
            case "Bandage":
                System.out.println(user.name + " uses Bandage and heals 1 HP.");
                user.heal(1);
                break;

            case "Knife":
                System.out.println(user.name + " uses Knife! The next shot does double damage.");
                user.hasKnife = true;
                break;

            case "Handcuffs":
                System.out.println(user.name + " uses Handcuffs! " + opponent.name + "'s next turn is skipped.");
                opponent.skipTurn = true;
                break;

            case "Morphine":
                System.out.println(user.name + " uses Morphine! They will block the next damage.");
                user.hasMorphine = true;
                break;

            default:
                System.out.println("Unknown item.");
                break;
        }
    }
}
