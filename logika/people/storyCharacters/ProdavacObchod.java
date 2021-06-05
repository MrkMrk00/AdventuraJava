package logika.people.storyCharacters;

import logika.items.Food;
import logika.items.Item;
import logika.items.Weapon;
import logika.people.Enemy;
import logika.people.Interactable;
import logika.people.MainCharacter;

import java.util.Arrays;

public class ProdavacObchod extends Enemy implements Interactable {

    private int interactOption;
    private final boolean[] viable;

    private final double weaponCost;

    public ProdavacObchod(String name, int baseAttack, Item availableForPurchase, double weaponCost) {
        super(name, baseAttack);
        this.weaponCost = weaponCost;
        this.getInventory().add(availableForPurchase);
        this.interactOption = -1;
        this.viable = new boolean[3];
        Arrays.fill(this.viable, false);
    }

    @Override
    public String interact(MainCharacter interacter) {
        if (!this.viable[0]) {
            this.interactOption = 0;
            Arrays.fill(this.viable, true);
        }


        switch (this.interactOption) {
            case 0:
                return "Dobrý den, co byste si přál?"
                        + (this.viable[1] ? System.lineSeparator() + "1 - Rád bych si koupil tuto kudlu." : "")
                        + (this.viable[2] ? System.lineSeparator() + "2 - Mohl bych si koupit tohle jablko?" : "");
            case 1:
                if (!this.viable[1]) break;
                this.interactOption = 0;
                return "Nemáte dostatek peněz, potřebujete " + this.weaponCost + ".";

            case 2:
                if (!this.viable[2]) break;
                this.interactOption = 0;
                if (interacter.getInventory().add(new Food("Jablko", interacter))) {
                    this.viable[2] = false;
                    return "Tady ho máte zadarmo :).";
                }
                return "Nemáte místo v inventáři";
        }
        return "Takovou možnost neznám.";
    }

    @Override
    public String interact(MainCharacter interacter, int option) {
        this.interactOption = option;
        return this.interact(interacter);
    }

}
