package logika.tests;

import logika.Hra;
import logika.SeznamPrikazu;
import logika.prikazy.Prikaz;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SeznamPrikazuTest {

    /**
     * Testuje metody vlozPrikaz(Prikaz), vratPrikaz(String), vratNazvyPrikazu(), jePlatnyPrikaz(Prikaz)<br>
     * Zkouší vložit nový příkaz do seznamu příkazů a pak testuje metody pro vrácení příkazů
     */
    @Test
    public void vlozPrikazTest() {
        Hra hra = new Hra();
        SeznamPrikazu seznam = new SeznamPrikazu();

        Prikaz novyPrikaz = new Prikaz(hra) {
            @Override
            public String provedPrikaz(String... parametry) {
                return "string k vraceni";
            }

            @Override
            public String getNazev() {
                return "novyprikaz";
            }
        };

        Assertions.assertEquals(seznam.vratNazvyPrikazu(), "");

        seznam.vlozPrikaz(novyPrikaz);
        Assertions.assertEquals(seznam.vratPrikaz("novyprikaz"), novyPrikaz);
        Assertions.assertTrue(seznam.vratNazvyPrikazu().startsWith("novyprikaz"));
        Assertions.assertTrue(seznam.jePlatnyPrikaz("novyprikaz"));
    }


}
