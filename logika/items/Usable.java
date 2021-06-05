package logika.items;

import logika.people.MainCharacter;

/**
 * Funkční interface, který je implementován předměty, na které lze zavolat příkaz pouzij<br>
 * Obsahuje pouze metodu use(), která je zavolána z třídy PrikazPouzij, nebo při sebrání předmětu, který má volání zadáno v metodě Item.onCollectToInventory(Hra hra) - tedy při použití příkazu seber
 */
@FunctionalInterface
public interface Usable {
    /**
     * Metoda zavolaná příkazem pouzij (logika.prikazy.PrikazPouzij)<br>
     * Může být zavolaná pouze, když je předmět v inventáři hlavní postavy - není potřeba propisovat hru
     * @return zpráva, která se propíše do konzole
     */
    String use();

    /**
     * Metoda s propisem hlavní postava
     * @param user hlavní postava
     * @return zpráva, která se propíše do konzole
     */
    default String use(MainCharacter user) {
        return use();
    }
}
