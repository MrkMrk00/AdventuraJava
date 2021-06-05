package logika;

import logika.items.Item;
import logika.people.Person;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Marek Hauschwitz
 * @version pro školní rok 2016/2017
 */
public class Prostor {

    private final String nazev;
    private final String popis;
    private final Set<Prostor> vychody;   // obsahuje sousední místnosti

    private final List<Item> contents;  //obsahuje předměty v místnosti
    private final List<Person> people;  //obsahuje lidi v místnosti

    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem" <br>
     * Inicializuje třídní proměnné vychody, contents a people
     *
     * @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     * víceslovný název bez mezer.
     * @param popis Popis prostoru.
     */
    public Prostor(String nazev, String popis) {
        this.nazev = nazev;
        this.popis = popis;
        this.vychody = new HashSet<>();
        this.contents = new ArrayList<>();
        this.people = new ArrayList<>();
    }

    /**
     * Přidává věci List Prostor.contents
     * @param item věc, která je k přidání do contents
     */
    public void add(Item item) {
        this.getItems().add(item);
    }

    /**
     * Přidává lidi do List Prostor.people
     * @param person člověk, který je k přidání do Prostor.people
     */
    public void add(Person person) {
        this.getPeople().add(person);
    }

    public void add(List<Person> people, List<Item> items, List<Prostor> vychody) {
        this.getPeople().addAll(people);
        this.getItems().addAll(items);
        vychody.forEach(this::setVychod);
    }



    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     *
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */  
    @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Prostor)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor 
        Prostor druhy = (Prostor) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

       return (java.util.Objects.equals(this.nazev, druhy.nazev));       
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }


    /**
     * Vrací "dlouhý" popis prostoru, který může vypadat následovně: Jsi v
     * mistnosti/prostoru vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return "Jsi v mistnosti/prostoru "
                + this.popis
                + "."
                + (this.popisVychodu().isBlank() ? "" : System.lineSeparator() + this.popisVychodu())
                + (this.popisContents().isBlank() ? "" : System.lineSeparator() + this.popisContents());
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    private String popisVychodu() {
        if (this.getVychody().isEmpty()) return "";
        StringBuilder s = new StringBuilder();
        s.append("Východy: ");
        this.getVychody().forEach(vychod -> s.append(vychod.getNazev() + " | "));
        s.delete(s.length() - 2, s.length() - 1);
        return s.toString();
    }

    /**
     * Vrátí zformátovaný String ve formátu: <br>
     *
     * Lidé v prostoru: clovek1, clovek2, ... <br>
     * Předměty v prostoru: předmět1, předmět2, ... <br>
     * <br>
     * @return String - seznam předmětů a lidí v prostoru
     */
    private String popisContents() {
        StringBuilder s = new StringBuilder();

        //pokud jsou v místnosti další lidé kromě hlavní postavy
        if (!this.people.isEmpty()) {
            s.append("Lidé v prostoru: ");
            this.people.forEach(person -> {
                s.append(person.getName() + ", ");
            });

            //smaže ", " za posledním člověkem
            s.delete(s.length() - 2, s.length() - 1);
        }

        //pokud jsou v místnosti předměty
        if (!this.contents.isEmpty()) {
            s.append(System.lineSeparator());
            s.append("Předměty v prostoru: ");
            this.contents.forEach(item -> {
                s.append(item.getName() + ", ");
            });

            //smaže ", " za posledním předmětem
            s.delete(s.length() - 2, s.length() - 1);
        }

        return s.toString();
    }

    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        List<Prostor> hledaneProstory =
            this.vychody.stream()
                   .filter(sousedni -> sousedni.getNazev().startsWith(nazevSouseda))
                   .collect(Collectors.toList());

        return (hledaneProstory.size() == 1 ? hledaneProstory.get(0) : null);
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }

    /**
     *
     * @return odkaz proměnnou contents
     */
    public List<Item> getItems() {
        return this.contents;
    }

    /**
     *
     * @return odkaz na proměnnou people
     */
    public List<Person> getPeople() {
        return this.people;
    }

    /**
     * Getter pro this.nazev
     *
     * @return název prostoru
     */
    public String getNazev() {
        return this.nazev;
    }
}
