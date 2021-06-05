package logika.people;

import logika.items.Item;

import java.util.List;

/**
 * Interface, který zadefinovává metody implementované třídami postav
 */
public interface IPerson {

    /**
     * Vrací jméno člověka/postavy
     * @return String jméno
     */
    String getName();

    /**
     * Vrací hodnotu true/false podle toho, jestli je člověk/postava naživu
     * @return boolean žije/nežije
     */
    boolean isLiving();

    /**
     * Vrací hodnotu počtu životů člověka/postavy
     * @return 0 <= int Health <= 100
     */
    int getHealth();

    /**
     * @return odkaz na inventář postavy/člověka
     */
    List<? extends Item> getInventory();

    /**
     * Metoda mění počet životů postavy/člověka<br>
     * Záporné hodnoty ubírají, kladné přidávají<br>
     * Maximální hodnota může být 100, minimální 0; při překročení se nastaví 100 nebo 0
     * @param amount hodnota o kterou životy změnit
     */
    void changeHealth(int amount);

    /**
     * Metoda zkotroluje, jestli je člověk/postava naživu<br>
     * Jestliže ne, nastaví hodnotu proměnné boolean living = false
     * @return hodnota Person.isLiving()
     */
    default boolean checkLiving() {
        return this.isLiving();
    }

    /**
     * @return hodnota útoku postavy
     */
    int getAttack();

    /**
     * Metoda by měla být zavolána po každém útoku<br>
     * Zjišťuje, jestli je zbraň použitá v boji zničitelná - pokud ano, tak ji odstraní z inventáře a vybaví novou nejsilnější
     */
    void callAfterAttack();
}
