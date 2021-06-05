package logika.people;

/**
 * Abstraktní class, která slouží ke generování postav - nepřátel za pomoci vytváření anonymních class, které implementují metodu Interactable.interact(...)
 */
public abstract class InteractableEnemy extends Enemy implements Interactable {

    /**
     * Konstruktor abstraktní třídy - je třeba implementovat pomocí anonymní class
     * @param name jméno nepřítele
     * @param baseAttack hodnota základního útoku
     */
    public InteractableEnemy(String name, int baseAttack) {
        super(name, baseAttack);
    }
}
