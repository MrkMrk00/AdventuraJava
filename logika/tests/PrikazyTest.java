package logika.tests;

import logika.Hra;
import logika.Prostor;
import logika.items.Item;
import logika.items.Usable;
import logika.people.Enemy;
import logika.people.Interactable;
import logika.people.MainCharacter;
import logika.people.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Třída s testy pro včechny příkazy
 */
public class PrikazyTest {
    private Hra hra;
    private final String itemName = "test_item_name";
    private final Item testItem = new Item(itemName);

    /**
     * Před každým testem se nově inicializuje proměnná s instancí hry
     */
    @BeforeEach
    public void init() {
        this.hra = new Hra();
    }

    private void addItemToAktualniProstor() {
        hra.getHerniPlan().getAktualniProstor().add(testItem);
        Assertions.assertTrue(hra.getHerniPlan().getAktualniProstor().getItems().contains(testItem));
    }

    private void addItemToCharacterInventory() {
        hra.getCharacter().getInventory().add(testItem);
        Assertions.assertTrue(hra.getCharacter().getInventory().contains(testItem));
    }

    private void existenceCheck(String prikaz) {
        Assertions.assertTrue(hra.getPlatnePrikazy().jePlatnyPrikaz(prikaz));
    }

    @Test
    public void testInventar() {
        addItemToCharacterInventory();
        existenceCheck("inventar");

        Assertions.assertTrue(hra.zpracujPrikaz("inventar").contains(itemName));
    }

    @Test
    public void testJdi() {
        existenceCheck("jdi");
        Prostor newNext = new Prostor("test", "test");

        hra.getHerniPlan().getAktualniProstor().setVychod(newNext);
        Assertions.assertTrue(hra.getHerniPlan().getAktualniProstor().getVychody().contains(newNext));

        hra.zpracujPrikaz("jdi test");
        Assertions.assertEquals(hra.getHerniPlan().getAktualniProstor(), newNext);
    }


    @Test
    public void testKonec() {
        existenceCheck("konec");
        Assertions.assertFalse(hra.konecHry());
        hra.zpracujPrikaz("konec");
        Assertions.assertTrue(hra.konecHry());
    }

    @Test
    public void testMluv() {
        existenceCheck("mluv");

        String interactReturn = "test_text_expected";
        String personName = "pavel_test_person";

        //třída, která je vytvořena pouze ke implementaci Interactable nad třídou Person
        abstract class InteractablePerson extends Person implements Interactable {
            public InteractablePerson(String name, int baseAttack) {
                super(name, baseAttack);
            }
        }

        hra.getHerniPlan().getAktualniProstor().add(new InteractablePerson(personName, 5) {
            @Override
            public String interact(MainCharacter interacter) {
                return interactReturn;
            }
        });

        //výpis do konzole musí obsahovat jak očekávanou hodnotu, tak jméno postavy
        Assertions.assertTrue(hra.zpracujPrikaz("mluv " + personName).contains(interactReturn));
        Assertions.assertTrue(hra.zpracujPrikaz("mluv " + personName).contains(personName));
    }

    @Test
    public void testNapoveda() {
        existenceCheck("nápověda");
        String nazvyPrikazu = hra.getPlatnePrikazy().vratNazvyPrikazu();
        Assertions.assertTrue(hra.zpracujPrikaz("nápověda").contains(nazvyPrikazu));

        hra.getPlatnePrikazy().getMapaSPrikazy()
                .forEach((key, prikaz) -> {
                    Assertions.assertEquals(key, prikaz.getNazev());
                    Assertions.assertTrue(nazvyPrikazu.contains(key));
                });
    }

    @Test
    public void testPouzij() {
        existenceCheck("pouzij");
        String usableItemName = "usable_item_test";
        String returnString = "expected_return_string";
        abstract class UsableItem extends Item implements Usable {
            public UsableItem(String name) {
                super(name);
            }
        }

        var usableItem = new UsableItem(usableItemName) {
            @Override
            public String use() {
                return returnString;
            }
        };

        hra.getCharacter().getInventory().add(usableItem);
        Assertions.assertEquals(hra.zpracujPrikaz("pouzij " + usableItemName), returnString);

        //zavolání příkazu pouzij s ID předmětu
        var usableItemID = hra.getCharacter().getInventory().get(hra.getCharacter().getInventory().indexOf(usableItem)).getID();
        Assertions.assertEquals(hra.zpracujPrikaz("pouzij ID " + usableItemID), returnString);
    }

    @Test
    public void testProhledej() {
        existenceCheck("prohledej");
        Person dead = new Enemy("test_person_name", 0);
        String testItemName = "test_item_name";
        Item item = new Item(testItemName);

        dead.getInventory().add(item);
        hra.getHerniPlan().getAktualniProstor().add(dead);
        hra.zpracujPrikaz("prohledej " + dead.getName());

        Assertions.assertTrue(hra.getHerniPlan().getAktualniProstor().getPeople().contains(dead));
        Assertions.assertFalse(hra.getHerniPlan().getAktualniProstor().getItems().contains(item));

        dead.changeHealth(-100);
        hra.zpracujPrikaz("prohledej " + dead.getName());

        Assertions.assertFalse(hra.getHerniPlan().getAktualniProstor().getPeople().contains(dead));
        Assertions.assertTrue(hra.getHerniPlan().getAktualniProstor().getItems().contains(item));
    }

    @Test
    public void testProstorInfo() {
        existenceCheck("prostorinfo");
        String returned = hra.zpracujPrikaz("prostorinfo");
        Assertions.assertTrue(returned.contains(hra.getHerniPlan().getAktualniProstor().dlouhyPopis()));
    }

    @Test
    public void testSeber() {
        addItemToAktualniProstor();
        existenceCheck("seber");

        hra.zpracujPrikaz("seber " + itemName);
        Assertions.assertTrue(hra.getCharacter().getInventory().contains(testItem));
    }

    @Test
    public void testSeberID() {
        addItemToAktualniProstor();
        var herniProstor = hra.getHerniPlan().getAktualniProstor();
        var itemID = herniProstor.getItems().get(herniProstor.getItems().indexOf(testItem)).getID();

        hra.zpracujPrikaz("seber ID " + itemID);
        Assertions.assertTrue(hra.getCharacter().getInventory().contains(testItem));
    }

    @Test
    public void testStats() {
        existenceCheck("stats");

        String returned = hra.zpracujPrikaz("stats");

        Assertions.assertTrue(returned.contains(String.valueOf(hra.getCharacter().getAttack())));
        Assertions.assertTrue(returned.contains(String.valueOf(hra.getCharacter().getHealth())));
        Assertions.assertTrue(returned.contains(String.valueOf(hra.getCharacter().getMoney())));
    }

    @Test
    public void testUtocWin() {
        existenceCheck("utoc");
        String expectedLosingText = "test_loss";
        var enemy = new Enemy("test_enemy_name", Math.min(hra.getCharacter().getAttack() * 2, 100)) {
            @Override
            protected String getLosingText(int remainingHealth) {
                return expectedLosingText;
            }
        };

        hra.getHerniPlan().getAktualniProstor().getPeople().add(enemy);

        Assertions.assertTrue(hra.getCharacter().isLiving());
        Assertions.assertTrue(enemy.isLiving());

        Assertions.assertTrue(hra.zpracujPrikaz("utoc " + enemy.getName()).contains(expectedLosingText));

        Assertions.assertFalse(hra.getCharacter().isLiving());
        Assertions.assertTrue(enemy.isLiving());
    }

    @Test
    public void testUtocLoss() {
        existenceCheck("utoc");
        String expectedWinningText = "test_win";
        var enemy = new Enemy("test_enemy_name", Math.min(hra.getCharacter().getAttack() / 2, 100)) {
            @Override
            protected String getWinningText(int remainingHealth) {
                return expectedWinningText;
            }
        };

        hra.getHerniPlan().getAktualniProstor().getPeople().add(enemy);

        Assertions.assertTrue(hra.getCharacter().isLiving());
        Assertions.assertTrue(enemy.isLiving());

        Assertions.assertTrue(hra.zpracujPrikaz("utoc " + enemy.getName()).contains(expectedWinningText));

        Assertions.assertTrue(hra.getCharacter().isLiving());
        Assertions.assertFalse(enemy.isLiving());
    }

    @Test
    public void testVyhod() {
        existenceCheck("vyhod");
        Item item = new Item("test_item_name");
        hra.getCharacter().getInventory().add(item);

        Assertions.assertFalse(hra.getHerniPlan().getAktualniProstor().getItems().contains(item));
        Assertions.assertTrue(hra.getCharacter().getInventory().contains(item));

        hra.zpracujPrikaz("vyhod " + item.getName());

        Assertions.assertTrue(hra.getHerniPlan().getAktualniProstor().getItems().contains(item));
        Assertions.assertFalse(hra.getCharacter().getInventory().contains(item));
    }
}