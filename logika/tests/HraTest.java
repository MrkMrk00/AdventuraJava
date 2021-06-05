package logika.tests;

import logika.Hra;
import logika.SeznamPrikazu;
import logika.prikazy.Prikaz;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class HraTest {
    private Hra hra;

    /**
     * Vytváří instanci třídy Hra před každým testem
     */
    @BeforeEach
    public void init() {
        this.hra = new Hra();
    }

    /**
     * Testuje mechaniku ukončení hry<br>
     * Metody konecHry() a setKonecHry()
     */
    @Test
    public void testKonec() {
        Assertions.assertFalse(hra.konecHry());
        hra.setKonecHry(true);
        Assertions.assertTrue(hra.konecHry());
    }

    /**
     * Testuje metodu zpracujPrikaz()
     */
    @Test
    public void zpracujPrikazTest() {
        String nazevPrikazu = "nameToCall";
        String vracenyPoZavolani = "stringReturned";
        String parametr = "parameter";

        this.hra.getPlatnePrikazy().vlozPrikaz(new Prikaz(this.hra) {
            @Override
            public String provedPrikaz(String... parametry) {
                return vracenyPoZavolani + parametry[0];
            }

            @Override
            public String getNazev() {
                return nazevPrikazu;
            }
        });

        Assertions.assertEquals(hra.zpracujPrikaz(nazevPrikazu + " " + parametr),
                vracenyPoZavolani + parametr);
    }

    /**
     * Testuje metodu jePlatnyPrikaz()<br>
     * Testuje, jestli se správně vložily herní příkazy do Hra.platnePrikazy
     */
    @Test
    public void testExistencePrikazu() {
        SeznamPrikazu platnePrikazy = this.hra.getPlatnePrikazy();
        Arrays.asList("jdi", "konec", "nápověda", "inventar", "seber", "stats", "pouzij", "vyhod", "prostorinfo", "mluv")
                .forEach(prikaz -> Assertions.assertTrue(platnePrikazy.jePlatnyPrikaz(prikaz)));

        Assertions.assertFalse(platnePrikazy.jePlatnyPrikaz("vylozeneneplatnyprikaz"));
    }

    @Test
    public void getterTest() {
        Assertions.assertNotNull(this.hra.getCharacter());
        Assertions.assertNotNull(this.hra.getPlatnePrikazy());
        Assertions.assertNotNull(this.hra.getHerniPlan());
    }
}
