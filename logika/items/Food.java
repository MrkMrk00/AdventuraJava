package logika.items;

import logika.people.MainCharacter;

/**
 * Třída věci - jídla, implemetuje interface Usable - po použití se odstraní z inventáře a přidá postavě životy
 */
public class Food extends Item implements Usable {
    private int healAmount;

    /**
     * Konstruktor věci - jídla s propsáním hlavní postavy
     * @param name jméno věci
     * @param character hlavní postava k propsání
     */
    public Food(String name, MainCharacter character) {
        super(name, character);
        this.healAmount = 100;
    }

    /**
     * Konstruktor věci - jídla pouze se jménem <br>
     * <strong>Musí dojít k sebrání pomocí příkazu seber, aby došlo k propsání hlavní postavy</strong>
     * @param name jméno věci
     */
    public Food(String name) {
        super(name);
        this.healAmount = 100;
    }

    /**
     * Konstruktor věci - jídla s propsáním hlavní postavy a nastavením množství životů k přidání
     * @param name jméno věci
     * @param character hlavní postava k propsání
     * @param healAmount množství životů, které jídlo přidá
     */
    public Food(String name, MainCharacter character, int healAmount) {
        super(name, character);
        this.healAmount = healAmount;
    }

    /**
     * Konstruktor věci - jídla s nastavením množství životů k přidání<br>
     * <strong>Musí dojít k sebrání pomocí příkazu seber, aby došlo k propsání hlavní postavy</strong>
     * @param name jméno věci
     * @param healAmount množství životů, které jídlo přidá
     */
    public Food(String name, int healAmount) {
        super(name);
        this.healAmount = healAmount;
    }


    /**
     * Nastaví počet životů, které jídlo uzdraví. Default je 100.
     * @param amount množství životů, které se nastaví a následně bude jídlo přidávat
     */
    public void setHealAmount(int amount) {
        this.healAmount = amount;
    }

    @Override
    public String use() {
        this.getCharacter().changeHealth(this.healAmount);
        this.getCharacter().getInventory().remove(this);
        return "Úspěšně ti byly přidány životy.";
    }

    @Override
    public String use(MainCharacter user) {
        this.setCharacter(user);
        return this.use();
    }
}
