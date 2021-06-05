package logika.prikazy;

import logika.Hra;
import logika.items.Item;

import java.util.Arrays;
import java.util.List;

public class PrikazSeber extends Prikaz {

    public PrikazSeber(Hra hra) {
        super(hra);
    }

    /**
     * Přidává předměty do inventáře postavy (this.getCharacter().inventory) <br>
     * <br>
     * Zakomentovaná část kódu - možnost dovolit přidávat pouze jednu věc do inventáře najednou
     *
     * @param parametry počet parametrů závisí na konkrétním příkazu.
     * @return String pro vypsání do konzole
     */

    @Override
    public String provedPrikaz(String... parametry) {
        ItemManipulator man = new ItemManipulator(this.getHerniPlan(), this.getCharacter());
        
        if (Arrays.asList(parametry).isEmpty()) return "Špatně zadáno - formát: seber *věc*"
                + System.lineSeparator()
                + "Nebo: seber ID *ID věci*";

        //část kódu pro sbírání pomocí ID
        if (parametry[0].equals("ID")) {
            if (parametry.length != 2) return "Špatně zadáno - formát: 'seber ID *ID věci*'";

            int itemID = -1;
            try {
                itemID = Integer.parseInt(parametry[1]);
            }
            catch (NumberFormatException e) {
                return "Špatně zadáno - formát: 'seber ID *ID veci*'";
            }

            //itemsCollected.size() by mělo být vždy 1; ID věcí by se neměla překrývat
            List<Item> itemsCollected = man.collectItems(this.getHerniPlan().getAktualniProstor().getItems(), itemID);
            if (itemsCollected.size() != 1) return "Nenalezena věc s takovým ID";
            Item collected = itemsCollected.get(0);

            //itemsCollected je obsazeno 1 položkou
            String onInventoryCollectMessage = man.moveToInventory(collected, this.getHra());

            return "Uspěšně přidána věc "
                    + collected.getName()
                    + " do inventáře"
                    + (onInventoryCollectMessage.isBlank() ? "" : System.lineSeparator() + onInventoryCollectMessage);
        }


        //vyhledávání věci podle jména
        StringBuilder s = new StringBuilder();
        Arrays.asList(parametry).forEach(parametr -> {
            s.append(parametr + " ");
        });

        List<Item> itemsCollected = man.collectItems(this.getHerniPlan().getAktualniProstor().getItems(), s.toString().strip());

        if (itemsCollected.isEmpty()) return "Žádná věc k sebrání nenalezena";
        if (itemsCollected.size() > 1) return "Nalezena více než jedna věc s tímto názvem";

        Item collected = itemsCollected.get(0);
        String onInventoryCollectMessage = man.moveToInventory(collected, this.getHra());

        if (this.getCharacter().getInventory().contains(collected)) {
            return "Úspěšně přidána věc ("
                    + collected.getName()
                    + ") do inventáře"
                    + (onInventoryCollectMessage.isBlank() ? "" : System.lineSeparator() + onInventoryCollectMessage);
        }
        return onInventoryCollectMessage;
    }

    @Override
    public String getNazev() {
        return "seber";
    }
}
