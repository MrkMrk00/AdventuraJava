package logika.prikazy;

import logika.Hra;


public class PrikazStats extends Prikaz {

    public PrikazStats(Hra hra) {
        super(hra);
    }

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length > 0) return "Nerozumim, syntaxe je pouze 'stats'";

        return "Životy: " + this.getCharacter().getHealth() + " | Peníze: " + this.getCharacter().getMoney() + " | Útok: " + this.getCharacter().getAttack();
    }

    @Override
    public String getNazev() {
        return "stats";
    }
}
