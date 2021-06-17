package logika.prikazy;

import logika.Hra;
import logika.people.Interactable;
import logika.people.Person;

import java.util.Arrays;
import java.util.List;

/**
 * Třída příkazu "mluv"<br>
 */
public class PrikazMluv extends Prikaz {

    public PrikazMluv(Hra hra) {
        super(hra);
    }

    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length < 1) return "Syntaxe je: 'mluv *člověk*'";

        StringBuilder s = new StringBuilder();
        ItemManipulator man = new ItemManipulator(this.getHerniPlan(), this.getCharacter());

        //spojení parametrů do jednoho stringu
        Arrays.asList(parametry).forEach(parametr -> {
            s.append(parametr + " ");
        });

        List<String> params = Arrays.asList(s.toString().split("-"));

        //Sesbírá všechny lidi v aktuální prostoru podle jména
        List<Person> collected = man.collectPeople(this.getHerniPlan().getAktualniProstor().getPeople(), params.get(0).strip());
        if (collected.size() != 1) return "Nenalezen právě jeden člověk s daným jménem";


        if (params.size() == 1 && collected.get(0) instanceof Interactable i)
            return collected.get(0).getName() + ": " + i.interact(this.getCharacter());


        if (params.size() == 2 && collected.get(0) instanceof Interactable i) {
            String optionRaw = params.get(1);
            int option;

            try {
                option = Integer.parseInt(optionRaw.strip());
                var up = new NumberFormatException();
                if (option < 0) throw up;
            }
            catch (NumberFormatException e) {
                return "Nepodařilo se přečíst volbu v konverzaci";
            }

            return collected.get(0).getName() + ": " + i.interact(this.getCharacter(), option);
        }

        if (params.size() > 2) return "Chyba v syntaxi příkazu";

        return "S tímto člověkem mluvit nemůžeš";
    }

    @Override
    public String getNazev() {
        return "mluv";
    }


}
