package logika.prikazy;

import logika.HerniPlan;
import logika.Hra;
import logika.items.Item;
import logika.people.MainCharacter;
import logika.people.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Třída pro zeštíhlení kódu v třídách příkazů <br>
 * Dostupná pouze v package logika.prikazy
 */
class ItemManipulator {

    private final HerniPlan herniPlan;
    private final MainCharacter character;
    
    ItemManipulator(HerniPlan herniPlan, MainCharacter character) {
        this.herniPlan = herniPlan;
        this.character = character;
    }

    /**
     * Sesbírá věci v seznamu podle jejich parametru name <br>
     * @param list z jakého seznamu filtrovat
     * @param name podle čeho filtrovat
     * @return seznam věcí
     */
     public List<Item> collectItems(List<Item> list, String name) {
        return list.stream()
                .filter(item -> item.getName().toLowerCase().startsWith(name.toLowerCase()))
                .collect(Collectors.toList());
     }

    /**
     * Sesbírá lidi v seznamu podle jejich parametru name <br>
     * @param list z jakého seznamu filtrovat
     * @param name podle čeho filtrovat
     * @return seznam lidí
     */
    public List<Person> collectPeople(List<Person> list, String name) {
        return list.stream()
                .filter(person -> person.getName().toLowerCase().startsWith(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Sesbírá lidi v seznamu podle jména, v metodě probíhá úprava String[] složením všech prvků do jednoho String s vloženými mezerami mezi prvky.<br>
     * @param list z jakého seznamu filtrovat
     * @param params pole s prvky - čásmi jména postavy
     * @return seznam nalezených lidí
     */
    public List<Person> collectPeopleFromParams(List<Person> list, String[] params) {
         StringBuilder s = new StringBuilder();
         Arrays.asList(params).forEach(param -> s.append(param + " "));

         return this.collectPeople(list, s.toString().strip());
    }

    /**
     * Hledá jednoho člověka z aktuální místnosti (herniPlan.getAktualniProstor()) podle jména sestaveného z pole
     * @param params String[] - pole s prvky = části jména hledané postavy
     * @return jeden člověk v případě, že je nalezen podle jména; jinak vrací null
     */
    public Person findPersonInCurrentRoom(String[] params) {
         List<Person> collected = this.collectPeopleFromParams(this.herniPlan.getAktualniProstor().getPeople(), params);
         if (collected.size() != 1) return null;
         return collected.get(0);
    }

    /**
     * Sesbírá věci v seznamu podle jejich parametru ID <br>
     * @param list z jakého seznamu filtrovat
     * @param ID podle čeho filtrovat
     * @return seznam věcí
     */
     public List<Item> collectItems(List<Item> list, int ID) {
        return list.stream()
                .filter(item -> item.getID() == ID)
                .collect(Collectors.toList());
     }

    /**
     * Přesune věci z prostoru do inventáře characteru (smaže z prostoru, přidá do inventáře)
     * @param toMove list věcí k přesunutí
     * @return výstup z metody Item.onCollectToInventory()
     */
    public String moveToInventory(List<Item> toMove, Hra hra) {
        StringBuilder s = new StringBuilder();
        toMove.forEach(item -> {
            if (this.character.getInventory().add(item)) {
                s.append(item.onCollectToInventory(hra) + System.lineSeparator());
                this.herniPlan.getAktualniProstor().getItems().remove(item);
            }
        });
        return s.toString().strip();
    }

    /**
     * Přesouvá pouze jednu věc z prostoru do inventáře
     * @param toMove věc k přesunutí
     * @return výstup z metody Item.onCollectToInventory()
     */
     public String moveToInventory(Item toMove, Hra hra) {
        if (this.character.getInventory().add(toMove)) {
            this.herniPlan.getAktualniProstor().getItems().remove(toMove);
            return toMove.onCollectToInventory(hra);
        }
        return "Tvůj inventář je plný (max 4 položky)";
    }

    /**
     * Přesouvá věc z inventáře do aktuálního prosoru
     * @param toMove List věcí k přesunutí
     */
     public void moveFromInventory(List<Item> toMove) {
        this.herniPlan.getAktualniProstor().getItems().addAll(toMove);
        this.character.getInventory().removeAll(toMove);
    }

    /**
     * Přesune pouze jednu věc z inventáře do aktuálního prosoru
     * @param toMove věc k přesunutí
     */
     public void moveFromInventory(Item toMove) {
        this.herniPlan.getAktualniProstor().getItems().add(toMove);
        this.character.getInventory().remove(toMove);
    }

    /**
     * Pouze odstraní věc z inventáře characteru
     * @param item jaký předmět odebrat
     */
     public void removeFromInventory(Item item) {
        this.character.getInventory().remove(item);
    }
}
