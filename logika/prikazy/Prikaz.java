package logika.prikazy;

import logika.HerniPlan;
import logika.Hra;
import logika.SeznamPrikazu;
import logika.people.MainCharacter;

/**
 * Abstraktní třída, implementuje metody z interface IPrikaz<br>
 * Dědí z ní třídy jednotlivých příkazů
 */
public abstract class Prikaz implements IPrikaz {
    private final Hra hra;
    private final HerniPlan herniPlan;
    private final MainCharacter character;
    private final SeznamPrikazu platnePrikazy;

    /**
     * Konstruktor nastavuje privátní proměnné instance třídy Prikaz<br>
     * @param hra instance hry
     */
    public Prikaz(Hra hra) {
        this.herniPlan = hra.getHerniPlan();
        this.character = hra.getCharacter();
        this.platnePrikazy = hra.getPlatnePrikazy();
        this.hra = hra;
    }

    public Hra getHra() {
        return hra;
    }

    public HerniPlan getHerniPlan() {
        return herniPlan;
    }

    public MainCharacter getCharacter() {
        return character;
    }

    public SeznamPrikazu getPlatnePrikazy() {
        return platnePrikazy;
    }
}
