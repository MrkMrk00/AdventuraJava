package logika.prikazy;

import logika.Hra;
import logika.items.Item;
import logika.people.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class PrikazProhledej extends Prikaz {
    /**
     * Konstruktor nastavuje privátní proměnné instance třídy Prikaz<br>
     *
     * @param hra instance hry
     */
    public PrikazProhledej(Hra hra) {
        super(hra);
    }

    @Override
    public String provedPrikaz(String... parametry) {
        Person toCollectFrom = new ItemManipulator(this.getHerniPlan(), this.getCharacter())
                .findPersonInCurrentRoom(parametry);

        if (toCollectFrom == null) return "Nenalezen člověk s daným jménem, nebo nalezeno více lidí.";
        if (toCollectFrom.isLiving()) return "Nemůžeš prohledat živého člověka";

        List<Item> inventory = toCollectFrom.getInventory();
        if (inventory.isEmpty()) return toCollectFrom.getName() + " u sebe nic nemá.";

        List<Item> invCopy = new ArrayList<>(inventory.size());
        invCopy.addAll(toCollectFrom.getInventory());
        invCopy.forEach(item -> {
            inventory.remove(item);
            this.getHerniPlan().getAktualniProstor().add(item);
        });
        this.getHerniPlan().getAktualniProstor().getPeople().remove(toCollectFrom);
        return "Do místnosti byly přidány nové předměty";
    }

    @Override
    public String getNazev() {
        return "prohledej";
    }
}
