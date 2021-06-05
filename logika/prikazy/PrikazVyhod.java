package logika.prikazy;

import logika.Hra;
import logika.items.Item;

import java.util.Arrays;
import java.util.List;

public class PrikazVyhod extends Prikaz {

    public PrikazVyhod(Hra hra) {
        super(hra);
    }

    @Override
    public String provedPrikaz(String... parametry) {
        ItemManipulator man = new ItemManipulator(this.getHerniPlan(), this.getCharacter());
        StringBuilder s = new StringBuilder();

        //vyhazování podle ID
        if (parametry[0].equals("ID")) {
            if (parametry.length != 2) return "Syntaxe je - 'vyhod ID *ID veci*";

            int itemID = -1;
            try {
                itemID = Integer.parseInt(parametry[1]);
            }
            catch (NumberFormatException e) {
                return "Syntaxe je - 'vyhod ID *ID veci*";
            }

            //itemToThrow.size() by melo byt vždy 1
            List<Item> itemToThrow = man.collectItems(this.getCharacter().getInventory(), itemID);
            man.moveFromInventory(itemToThrow);
            return "Uspěšně vyhozena věc " + itemToThrow.get(0).getName();
        }


        Arrays.asList(parametry).forEach(parametr -> s.append(parametr + " "));
        List<Item> fromInventory = man.collectItems(this.getCharacter().getInventory(), s.toString().strip());

        if (fromInventory.isEmpty()) return "Nenalezena ani jedna věc s daným jménem";
        if (fromInventory.size() > 1) return "Nenalezena právě jedna věc k vyhození. Zkus použít vyhození podle ID - 'vyhod ID *ID veci*";

        man.moveFromInventory(fromInventory);
        return "Úspěšně vyhozena věc " + fromInventory.get(0).getName();
    }

    @Override
    public String getNazev() {
        return "vyhod";
    }
}
