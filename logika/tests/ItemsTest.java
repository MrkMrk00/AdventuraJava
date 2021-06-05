package logika.tests;

import logika.Hra;
import logika.items.*;
import logika.people.Enemy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Třída testů pro všechny třídy věcí (testuje logiku věcí)
 */
public class ItemsTest {
    private Hra hra;

    @BeforeEach
    public void init() {
        this.hra = new Hra();
    }

    @Test
    public void foodTest() {
        int healAmount = 25;
        int healthRemove = 50;
        Food food = new Food("test_food_name", healAmount);
        hra.getCharacter().changeHealth(-healthRemove);
        Assertions.assertEquals(100 - healthRemove, hra.getCharacter().getHealth());
        food.use(hra.getCharacter());
        Assertions.assertEquals(100 - healthRemove + healAmount, hra.getCharacter().getHealth());
    }

    @Test
    public void moneyContainerTest() {
        double moneyAmount = 200;
        MoneyContainer container = new MoneyContainer("test_container_name", moneyAmount);
        Assertions.assertEquals(0.0, hra.getCharacter().getMoney());
        container.use(hra.getCharacter());
        Assertions.assertEquals(moneyAmount, hra.getCharacter().getMoney());
    }

    @Test
    public void moneyContainerOnCollectToInventoryTest() {
        double moneyAmount = 200;
        MoneyContainer container = new MoneyContainer("test_container_name", moneyAmount);
        Assertions.assertEquals(0.0, hra.getCharacter().getMoney());
        hra.getHerniPlan().getAktualniProstor().getItems().add(container);
        hra.zpracujPrikaz("seber " + container.getName());
        Assertions.assertFalse(hra.getHerniPlan().getAktualniProstor().getItems().contains(container));
        //container by se po sebrání měl smazat z inventáře postavy
        Assertions.assertFalse(hra.getCharacter().getInventory().contains(container));
        Assertions.assertEquals(moneyAmount, hra.getCharacter().getMoney());
    }

    @Test
    public void transformingItemTest() {
        Item itemToTransform = new Item("test_toTransform_name");
        TransformingItem item = new TransformingItem("test_item_name", itemToTransform);
        hra.getHerniPlan().getAktualniProstor().add(item);

        hra.zpracujPrikaz("seber " + item.getName());
        Assertions.assertFalse(hra.getHerniPlan().getAktualniProstor().getItems().contains(item));
        Assertions.assertFalse(hra.getHerniPlan().getAktualniProstor().getItems().contains(itemToTransform));
        Assertions.assertTrue(hra.getCharacter().getInventory().contains(item));
        Assertions.assertFalse(hra.getCharacter().getInventory().contains(itemToTransform));

        hra.zpracujPrikaz("pouzij " + item.getName());
        Assertions.assertFalse(hra.getCharacter().getInventory().contains(item));
        Assertions.assertTrue(hra.getCharacter().getInventory().contains(itemToTransform));
    }

    @Test
    public void weaponTest() {
        int attack = 20;
        Weapon weapon = new Weapon("weapon_test_name", attack, true);
        Enemy enemy = new Enemy("enemy_test_name", 1);

        hra.getCharacter().getInventory().add(weapon);
        hra.getHerniPlan().getAktualniProstor().add(enemy);

        Assertions.assertEquals(attack, hra.getCharacter().getAttack());

        //test consumable weapon
        hra.zpracujPrikaz("utoc " + enemy.getName());
        Assertions.assertFalse(hra.getCharacter().getInventory().contains(weapon));
        Assertions.assertFalse(enemy.isLiving());
    }
}
