package logika.tests;

import logika.Prostor;
import logika.items.Item;
import logika.people.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ProstorTest {
    private Prostor jedna;
    private Prostor dva;

    @BeforeEach
    public void init() {
        this.jedna = new Prostor("jedna", "jedna");
        this.dva = new Prostor("dva", "dva");
    }

    /**
     * Testuje správnost fungování mechaniky sousedních prostorů
     */
    @Test
    public void vedlejsiProstorTest() {
        this.jedna.setVychod(dva);

        Assertions.assertTrue(this.jedna.getVychody().contains(this.dva));
        Assertions.assertFalse(this.dva.getVychody().contains(this.jedna));

        Assertions.assertEquals(this.jedna.vratSousedniProstor("dva"), this.dva);
        Assertions.assertNull(this.dva.vratSousedniProstor("jedna"));
    }

    @Test
    public void getterTest() {
        var items = jedna.getItems();
        Assertions.assertNotNull(items);
        Assertions.assertTrue(items instanceof List<? extends Item>);

        var people = jedna.getPeople();
        Assertions.assertNotNull(people);
        Assertions.assertTrue(people instanceof List<? extends Person>);
    }
}
