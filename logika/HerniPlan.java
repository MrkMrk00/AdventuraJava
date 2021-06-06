package logika;

import logika.items.*;
import logika.people.Enemy;
import logika.people.InteractableEnemy;
import logika.people.MainCharacter;
import logika.people.Person;
import logika.people.storyCharacters.*;
import java.util.List;


/**
 *  Class HerniPlan - třída představující mapu a stav adventury.
 * 
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů,
 *  přidá do prostorů věci a lidi
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Marek Hauschwitz
 *@version    pro školní rok 2016/2017
 */
public class HerniPlan {
    private Prostor aktualniProstor;

    /**
     *  Konstruktor <br>
     *  Vytvoří prostory hry, nastaví jejich východy, přidá do prostorů předměty a lidi, inicializuje třídní proměnnou aktualniProstor počátečním prostorem.
     */
    public HerniPlan() {
        Prostor zachytnaStanice = new Prostor("Před záchytnou stanicí","Ulice před záchytnou stanicí");
        Prostor uvnitrZachytneStanice = new Prostor("Uvnitř záchtné stanice", "Uvnitř záchtné stanice");
        Prostor ulicka = new Prostor("Ulička", "Ulička mezi domy u záchytné stanice");
        Prostor mesto = new Prostor("Město", "město, propojuje prostory");
        Prostor benzinka = new Prostor("Benzínka","tankovací stanice");
        Prostor obchod = new Prostor("Obchod", "obchod");
        Prostor most = new Prostor("Pod mostem","Prostor pod mostem, vhodný ke spánku");
        Prostor OU = new Prostor("Obecní úřad","Obecní úřad, tady se platí");

        this.aktualniProstor = zachytnaStanice;

        zachytnaStanice.setVychod(ulicka);
        mesto.setVychod(zachytnaStanice);
        ulicka.setVychod(zachytnaStanice);
        List.of(benzinka, most, OU, obchod).forEach(prostor -> {prostor.setVychod(mesto); mesto.setVychod(prostor);});

        //příběhové předměty a postavy

        Item vypitejVajnos = new Item("Rulandské bílé 0,5 l - krabičák (vypitej)");
        Item vajnos = new TransformingItem("Rulandské bílé 0,5 l - krabičák", vypitejVajnos) {
            @Override
            public String use() {
                this.getCharacter().changeHealth(100);
                super.use();
                return "Vypil jsi krabičák";
            }
        };
        zachytnaStanice.add(new Franta("Franta - bezdomovec", 5, vajnos, vypitejVajnos, zachytnaStanice, mesto));

        Enemy enemakTomas = new Enemy("Tomáš - bezdomovec", 15);
        enemakTomas.getInventory().addAll(List.of(vajnos, new MoneyContainer("Drobný", 9)));
        ulicka.add(enemakTomas);

        Item obcanka = new Item("Občanský průkaz - Hlavní postava");
        zachytnaStanice.add(new StraznikZachytneStanice("Strážník",
                100,
                obcanka,
                zachytnaStanice,
                uvnitrZachytneStanice)
        );
        uvnitrZachytneStanice.add(new Vyberci("Výběrčí poplatků", 1));

        Person prodavac = new ProdavacBenzinky("Prodavač", 25, obcanka);
        prodavac.getInventory().add(new MoneyContainer("Peníze z kasy", 491));
        benzinka.add(prodavac);

        Person prodavac2 = new ProdavacObchod(
                "Prodavač",
                25,
                new TransformingItem("Zabalená kudla",
                        new Weapon("Kudla",
                            60,
                            false)),
                509.99);

        prodavac2.getInventory().addAll(List.of(
                new MoneyContainer("Drobný", 1),
                new Food("Lahváč", 50)
                )
        );
        obchod.add(prodavac2);

        most.add(new TransformingItem("Kus drátu", new Weapon("DIY Nunčaky",34, true)));

        Person politik = new InteractableEnemy("Lokální politik", 25) {
            @Override
            public String interact(MainCharacter interacter) {
                return "Co chceš?! S bezďákama se nebavim...";
            }
        };
        politik.getInventory().add(new MoneyContainer("Peněženka", 1999));
        OU.add(politik);
    }

    /**
     * Getter pro this.aktualniProstor
     *
     * @return     aktuální prostor
     */
    public Prostor getAktualniProstor() {
        return this.aktualniProstor;
    }
    
    /**
     *  Setter pro this.aktualniProstor
     *
     *  @param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
       this.aktualniProstor = prostor;
    }
}
