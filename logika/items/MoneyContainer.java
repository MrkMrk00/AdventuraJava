package logika.items;

import logika.Hra;
import logika.people.MainCharacter;

/**
 * Třída věci, která obsahuje peníze. Při sebrání této věci příkazem seber se věc smaže z inventáře postavy a přidá mu peníze.
 */
public class MoneyContainer extends Item implements Usable {

    private final double moneyAmount;

    /**
     * Konstruktor věci, která přidá peníze postavě<br>
     * Věc bude vždy sebrána příkazem seber - vždy proběhne metoda onCollectToInventory - nevznikne NullPointerException při zavolání metody nad this.getCharacter()
     * @param name jméno věci
     * @param moneyAmount množství peněz, které se přidají při sebrání věci
     */
    public MoneyContainer(String name, double moneyAmount) {
        super(name);
        this.moneyAmount = moneyAmount;
    }

    /**
     * Metoda se zavolá vždy při sebrání věci do inventáře<br>
     * @return String, který se vypíše do konzole (množství peněz přidáno)
     */
    @Override
    public String use() {
        this.getCharacter().addMoney(this.moneyAmount);
        this.getCharacter().getInventory().remove(this);
        return "Úspěšně přidány peníze v hodnotě " + this.moneyAmount;
    }

    @Override
    public String use(MainCharacter user) {
        this.setCharacter(user);
        return this.use();
    }

    @Override
    public String onCollectToInventory(Hra hra) {
        super.onCollectToInventory(hra);
        return this.use();
    }
}
