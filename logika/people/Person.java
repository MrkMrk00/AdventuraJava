package logika.people;

import logika.items.Item;
import logika.items.Weapon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Třída obecné postavy
 */
public class Person implements IPerson {
    private String name;
    private int health;
    private final List<Item> inventory;
    private boolean living;

    private final Weapon fists;
    private Weapon equippedWeapon;

    /**
     * Konstruktor obecné postavy<br>
     * Vytváří inventář postavy (anonymous class, která dědí z ArrayList), přepisuje metody ArrayList.add(Item item) a ArrayList.addAll(Collection<? extends Item> c), aby bylo možné do inventáře přidat pouze 4 věci
     * @param name jméno postavy
     * @param baseAttack základní hodnota útoku postavy
     */
    public Person(String name, int baseAttack) {
        this.name = name;

        //anonymní class, přepisuje metodu add a addAll, aby umožňovala přidání pouze 4 věcí do inventáře
        this.inventory = new ArrayList<>() {
            @Override
            public boolean add(Item item) {
                if (this.size() < 4) {
                    return super.add(item);
                }
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Item> c) {
                if (this.size() + c.size() <= 4) {
                    return super.addAll(c);
                }
                return false;
            }
        };
        this.fists = new Weapon("Fists", baseAttack, false);
        this.equippedWeapon = fists;
        this.health = 100;
        this.living = true;
    }

    @Override
    public void changeHealth(int amount) {
        if (this.health + amount < 0) this.health = 0;
        else if (this.health + amount >= 100) this.health = 100;
        else this.health += amount;

        this.checkLiving();
    }

    @Override
    public boolean checkLiving() {
        if (health <= 0) {
            this.living = false;
            this.name = "Mrtvola - " + this.name;
        }
        return this.isLiving();
    }

    @Override
    public void callAfterAttack() {
        if (this.equippedWeapon.isConsumable()) this.inventory.remove(this.equippedWeapon);
        this.equipStrongestWeapon();
    }

    /**
     * Metoda, která udělá ForEach nad inventářem a vybere z něj zbraň (instance Weapon) s nejvyšším útokem a vybaví jí (nastaví jí do proměnné this.equipedWeapon)<br>
     * Pokud není žádná zbraň v inventáři, nebo je útok nejsilnější zbraně nižší, než baseAttack zadaný při vytvoření instance ? extends Person, tak se vybaví this.fists s útokem baseAttack
     */
    private void equipStrongestWeapon() {
        Weapon currentStrongest = this.fists;
        for (Item item : this.inventory) {
            if (item instanceof Weapon weapon
                    && weapon.getDamage() > currentStrongest.getDamage())
                currentStrongest = weapon;
        }
        this.equippedWeapon = currentStrongest;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Item> getInventory() {
        return this.inventory;
    }

    @Override
    public boolean isLiving() {
        return this.living;
    }

    @Override
    public int getHealth() {
        return health;
    }
    @Override
    public int getAttack() {
        this.equipStrongestWeapon();
        return this.equippedWeapon.getDamage();
    }
}
