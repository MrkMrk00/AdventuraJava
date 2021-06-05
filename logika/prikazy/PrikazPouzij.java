package logika.prikazy;

import logika.Hra;
import logika.items.Item;
import logika.items.Usable;
import java.util.Arrays;
import java.util.List;

public class PrikazPouzij extends Prikaz {

    public PrikazPouzij(Hra hra) {
        super(hra);
    }

    @Override
    public String provedPrikaz(String... parametry) {
        String syntaxErrMessage = """
                Syntaxe je: pouzij ID *id věci*, nebo
                pouzij *nazev veci*
                """;
        String itemNotFoundErrMessage = "Nepodařilo se najít věc s daným názvem. :(";
        String itemNotUsableErrMessage = "Tuto věc nelze použít";
        if (!(parametry.length > 0)) return syntaxErrMessage;

        ItemManipulator man = new ItemManipulator(this.getHerniPlan(), this.getCharacter());
        StringBuilder s = new StringBuilder();

        //výběr předmětu pomocí ID
        if (parametry[0].equals("ID")) {
            if (parametry.length != 2) return syntaxErrMessage;
            List<Item> itemsCollected;

            try {
                itemsCollected = man.collectItems(this.getCharacter().getInventory(), Integer.parseInt(parametry[1]));
            } catch (NumberFormatException e) {
                return syntaxErrMessage;
            }

            if (itemsCollected.size() != 1) return itemNotFoundErrMessage;
            if (itemsCollected.get(0) instanceof Usable u) return u.use(this.getCharacter());
            return itemNotUsableErrMessage;
        }

        //výběr předmětu pomocí jména předmětu
        //sloučení parametrů do jednoho String pro podporu víceslovných názvů předmětů
        Arrays.asList(parametry).forEach(param -> s.append(param + " "));
        List<Item> itemsCollected = man.collectItems(this.getCharacter().getInventory(), s.toString().strip());

        if (itemsCollected.size() != 1) return itemNotFoundErrMessage;
        if (itemsCollected.get(0) instanceof Usable u) return u.use(this.getCharacter());
        return itemNotUsableErrMessage;
    }

    @Override
    public String getNazev() {
        return "pouzij";
    }
}
