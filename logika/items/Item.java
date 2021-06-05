package logika.items;

import logika.Hra;
import logika.people.MainCharacter;

/**
 * Třída obecného předmětu, většina logiky předmětů se nachází zde<br>
 * Třída obsahuje generátor ID - třídní proměnná currentHighestID, kterou se iteruje a přiřazuje se předmětům
 */
public class Item {
    private static int currentHighestID = 0;

    private final String name;
    private final int ID;
    private Hra hra;
    private MainCharacter character;

    /**
     * Konstruktor předmětu<br>
     * Přidá ke statické proměnné Item.currentHighestID +1, nastaví instanci unikátní hodnotu ID
     * @param name jméno předmětu
     */
    public Item(String name) {
        this.name = name;
        this.ID = currentHighestID++;
    }

    /**
     * Konstruktor předmětu<br>
     * Přidá ke statické proměnné Item.currentHighestID +1, nastaví instanci unikátní hodnotu ID<br>
     * Nastaví
     * @param name jméno předmětu
     * @param hra propis instance hry
     */
    public Item(String name, Hra hra) {
        this.name = name;
        this.ID = currentHighestID++;
        this.hra = hra;
        this.character = hra.getCharacter();
    }

    /**
     * Konstruktor předmětu<br>
     * Přidá ke statické proměnné Item.currentHighestID +1, nastaví instanci unikátní hodnotu ID
     * @param name jméno předmětu
     * @param character propis instance hlavní postavy
     */
    public Item(String name, MainCharacter character) {
        this.name = name;
        this.ID = currentHighestID++;
        this.character = character;
    }

    /**
     * Zavolá se vždy pro přidání Item do inventáře characteru. <br>
     * Defaultní return hodnota - "", aby se zabránilo NullPointerException při @Override v classach, které z této dědí <br>
     * Neočekává se, že bude definovaná u každého předmětu
     * @param hra inicializuje proměnnou hra a character
     * @return zpráva, která se propíše do konzole
     */
    public String onCollectToInventory(Hra hra) {
        this.hra = hra;
        this.character = hra.getCharacter();
        return "";
    }

    /**
     * Getter pro Item.ID - jedinečné číslo každého předmětu určené k identifikaci/diferenciaci
     * @return int ID předmětu
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Getter pro proměnnou obsahující jméno předmětu
     * @return String jméno předmětu
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter, který mohou volat pouze předměty (které dědí z této class)
     * @return instanci hry
     */
    protected Hra getHra() {
        return this.hra;
    }

    /**
     * Getter, který mohou volat pouze předměty (které dědí z této class)
     * @return instanci hlavní postavy
     */
    protected MainCharacter getCharacter() {
        return this.character;
    }

    /**
     * Setter nastavení proměnné hra
     * @param hra instance hry
     */
    public void setHra(Hra hra) {
        this.hra = hra;
        this.character = hra.getCharacter();
    }

    /**
     * Nastavuje odkaz na hlavní postavu
     * @param character hlavní postava
     */
    public void setCharacter(MainCharacter character) {
        this.character = character;
    }

}
