package logika.prikazy;

import logika.Hra;
import logika.people.Attackable;

import java.util.Arrays;
import java.util.List;

public class PrikazUtoc extends Prikaz {

    public PrikazUtoc(Hra hra) {
        super(hra);
    }

    @Override
    public String provedPrikaz(String... parametry) {
        ItemManipulator man = new ItemManipulator(this.getHerniPlan(), this.getCharacter());
        StringBuilder sb = new StringBuilder();
        Arrays.asList(parametry).forEach(parametr -> sb.append(parametr + " "));
        List<?> collected =  man.collectPeople(this.getHerniPlan().getAktualniProstor().getPeople(), sb.toString().strip());

        if (collected.size() != 1) return "Nenalezen člověk s takovým jménem, nebo nalezeno více lidí.";
        if (collected.get(0) instanceof Attackable a) return a.attack(this.getCharacter());
        return "Na tohoto člověka nemůžeš zaútočit";
    }

    @Override
    public String getNazev() {
        return "utoc";
    }
}
