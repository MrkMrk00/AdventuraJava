package logika.people;

/**
 * Funkční interface, implementují ho třídy postav, na které je možné útočit
 */
@FunctionalInterface
public interface Attackable {
    /**
     * Implementuje logiku souboje
     * @param attacker jaká postava na danou útočí
     * @return String k vypsání do konzole
     */
    String attack(MainCharacter attacker);
}
