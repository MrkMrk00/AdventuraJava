package logika.items;

/**
 * Třída věci - zbraň<br>
 * Má útok a boolean proměnnou, která říká, jestli je věc použitelná vícekrát (false), nebo pouze jednou (true).<br>
 * Zbraně jsou věci, které jsou běžně v inventáři a mohou být vybaveny - nastavují útok postavě, která má danou zbraň v inventáři.
 * Při vybavení zbraně zůstávají v inventáři a stále zabírají místo. Vybaví se pouze, pokud mají vyšší hodnotu útoku než další zbraně v inventáři,
 * nebo v případě, že je v inventáři pouze jediná zbraň, pouze pokud má vyšší útok než baseAttack postavy - útok defaultní zbraně fists
 */
public class Weapon extends Item {

    private final int damage;
    private final boolean consumable;

    /**
     * Konstruktor zbraně
     * @param name jméno zbraně
     * @param damage útok zbraně
     * @param consumable nastavení, zda-li je zbraň použitelná vícekrát (false), nebo jen jednou (true)
     */
    public Weapon(String name, int damage, boolean consumable) {
        super(name);
        this.damage = damage;
        this.consumable = consumable;
    }

    /**
     * Getter pro proměnnou útoku
     * @return instanční proměnnou útoku
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Vrací boolean hodnotu, zda-li je zbraň spotřební
     * @return boolean consumable
     */
    public boolean isConsumable() {
        return this.consumable;
    }
}
