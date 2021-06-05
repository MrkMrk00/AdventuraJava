package logika.tests;

import logika.Hra;
import logika.items.Item;
import logika.items.Weapon;
import logika.people.Enemy;
import logika.people.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PeopleTest {
    private Hra hra;
    private Person person;

    @BeforeEach
    public void init() {
        this.hra = new Hra();
        this.person = new Person("test", 5);
    }

    @Test
    public void maxInventorySize() {
        Assertions.assertEquals(0, hra.getCharacter().getInventory().size());
        Assertions.assertTrue(hra.getCharacter().getInventory().add(new Item("1")));
        Assertions.assertEquals(1, hra.getCharacter().getInventory().size());

        hra.getCharacter().getInventory().addAll(List.of(
                new Item("2"),
                new Item("3"),
                new Item("4")
        ));

        Assertions.assertEquals(4, hra.getCharacter().getInventory().size());
        Assertions.assertFalse(hra.getCharacter().getInventory().add(new Item("5")));
        Assertions.assertFalse(hra.getCharacter().getInventory().addAll(List.of(
                new Item("6"),
                new Item("7")
        )));
        Assertions.assertEquals(4, hra.getCharacter().getInventory().size());
    }

    @Test
    public void changeHealthTest() {
        Assertions.assertEquals(100, person.getHealth());
        person.changeHealth(20);
        Assertions.assertEquals(100, person.getHealth());
        person.changeHealth(-20);
        Assertions.assertTrue(person.getHealth() < 100);
        person.changeHealth(-150);
        Assertions.assertEquals(0, person.getHealth());
    }

    @Test
    public void checkLivingTest() {
        Assertions.assertTrue(person.checkLiving());
        person.changeHealth(-100);
        Assertions.assertFalse(person.checkLiving());
    }

    @Test
    public void equipWeaponTest() {
        Weapon w = new Weapon("stronger_weapon_test_name", 20, true);
        Weapon w1 = new Weapon("weaker_weapon_test_name", 15, true);
        Enemy e = new Enemy("enemy_test_name", 1);

        hra.getCharacter().getInventory().addAll(List.of(w, w1));
        Assertions.assertEquals(20, hra.getCharacter().getAttack());

        e.attack(hra.getCharacter());
        Assertions.assertFalse(hra.getCharacter().getInventory().contains(w));
        Assertions.assertEquals(15, hra.getCharacter().getAttack());
        Assertions.assertTrue(hra.getCharacter().getInventory().contains(w1));
        Assertions.assertFalse(e.isLiving());
    }

    @Test
    public void attackWinTest() {
        Enemy e = new Enemy("enemy_test_name", 5);
        hra.getCharacter().getInventory().add(new Weapon("weapon_test_name", 10, false));
        e.attack(hra.getCharacter());
        Assertions.assertFalse(e.isLiving());
        Assertions.assertEquals(100 - (100/10) * 5, hra.getCharacter().getHealth());
    }

    @Test
    public void attackLossTest() {
        Enemy e = new Enemy("enemy_test_name", 20);
        e.attack(hra.getCharacter());
        Assertions.assertFalse(hra.getCharacter().isLiving());
        Assertions.assertEquals(100 - (100/10) * 5, e.getHealth());
    }
}
