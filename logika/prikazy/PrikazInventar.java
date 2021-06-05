package logika.prikazy;

import logika.Hra;

/**
 * Třída příkazu inventář<br>
 * Příkaz vypíše všechny věci, které má u sebe postava v inventáři
 */
public class PrikazInventar extends Prikaz {

    public PrikazInventar(Hra hra) {
        super(hra);
    }


    @Override
    public String provedPrikaz(String... parametry) {
        StringBuilder s = new StringBuilder();
        s.append("Věci v inventáři: ");

        if (this.getCharacter().getInventory().isEmpty()) return s.toString();

        this.getCharacter().getInventory().forEach(item -> {
            s.append("#" + item.getID() + " ");
            s.append(item.getName() + ", ");
        });

        s.delete(s.length() - 2, s.length() - 1);

        return s.toString();
    }

    @Override
    public String getNazev() {
        return "inventar";
    }
}
