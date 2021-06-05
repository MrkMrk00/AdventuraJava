package logika.people;

/**
 * Funkční interface, která je implemetován postavami, se kterými je možné mluvit
 */
@FunctionalInterface
public interface Interactable {
    /**
     * Metoda s logikou po použití příkazu "mluv"
     * @param interacter postava, která s danou mluví (MainCharacter)
     * @return String k vypsání do konzole
     */
    String interact(MainCharacter interacter);

    /**
     * Metoda interact s možností určit možnost mluvení
     * @param interacter postava, která s danou mluví (MainCharacter)
     * @param option nastavení možnosti odpovědi
     * @return String k vypsání do konzole
     */
    default String interact(MainCharacter interacter, int option) {
        return this.interact(interacter);
    }
}
