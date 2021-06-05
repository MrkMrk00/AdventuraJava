package logika;

import logika.people.MainCharacter;
import logika.prikazy.IPrikaz;
import logika.prikazy.*;

import java.util.Arrays;

/**
 *  Třída Hra - třída představující logiku adventury.
 * 
 *  Toto je hlavní třída  logiky aplikace. Tato třída vytváří instanci třídy HerniPlan, která inicializuje místnosti hry
 *  a vytváří seznam platných příkazů a instance tříd provádějící jednotlivé příkazy.
 *  Vytváří instanci hlavní postvy.
 *  Má definované metody pro uvítání a rozloučení se s hráčem.
 *  Manipuluje s inputem uživatele.
 *  Nastavuje konec hry.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Marek Hauschwitz
 *@version    pro školní rok 2016/2017
 */
public class Hra implements IHra {

    public static boolean konecHry = false;

    private final MainCharacter character;
    private final HerniPlan herniPlan;
    private final SeznamPrikazu platnePrikazy;

    /**
     *  Konstruktor<br>
     *  Inicializuje character, herniPlan a platnePrikazy<br>
     *  Vytvoří instance tříd příkazů a přidá je do platnePrikazy
     *
     */
    public Hra() {
        this.herniPlan = new HerniPlan();
        this.character = new MainCharacter(this, 10);
        this.platnePrikazy = new SeznamPrikazu();

        // přidávání příkazů do platnePrikazy
        Arrays.asList(
                new PrikazJdi(this),
                new PrikazKonec(this),
                new PrikazNapoveda(this),
                new PrikazInventar(this),
                new PrikazSeber(this),
                new PrikazStats(this),
                new PrikazPouzij(this),
                new PrikazVyhod(this),
                new PrikazProstorInfo(this),
                new PrikazMluv(this),
                new PrikazUtoc(this),
                new PrikazProhledej(this)
        ).forEach(this.platnePrikazy::vlozPrikaz);
    }

    /**
     *  Vrátí úvodní zprávu pro hráče.
     */
    public String vratUvitani() {
        return """
               Dobrý den, vítejte u hry!
               Jste bezdomovec, který se předchozí den značně zpil.
               Nyní se nacházíte před záchytnou stanicí, kde jste strávili noc a nyní po vás chtějí zaplatit nějaké peníze.
               Když si nebudete vědět rady, tak napište příkaz "nápověda"
               
               """
                + this.herniPlan.getAktualniProstor().dlouhyPopis();
    }
    
    /**
     *  Vrátí závěrečnou zprávu pro hráče.
     */
    public String vratEpilog() {
        return "Dík, že jste si zahráli.  Ahoj.";
    }
    
    /** 
     * Vrací true, pokud hra skončila.
     */
     public boolean konecHry() {
        return Hra.konecHry;
    }

    /**
     *  Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     *  Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     *  Pokud ano spustí samotné provádění příkazu.
     *
     *@param  radek  text, který zadal uživatel jako příkaz do hry.
     *@return          vrací se řetězec, který se má vypsat na obrazovku
     */
     public String zpracujPrikaz(String radek) {
        //String [] slova = radek.split("[ \t]+");
        String[] slova = radek.split(" ");
        String slovoPrikazu = slova[0];
        String []parametry = new String[slova.length-1];
        for(int i=0 ;i<parametry.length;i++){
           	parametry[i]= slova[i+1];  	
        }
        String textKVypsani=" .... ";
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.provedPrikaz(parametry);
        }
        else {
            textKVypsani="Nevím co tím myslíš? Tento příkaz neznám. ";
        }
        return textKVypsani;
    }
    
    
     /**
     *  Nastaví, že je konec hry, metodu využívá třída PrikazKonec,
     *  mohou ji použít i další implementace rozhraní Prikaz.
     *  
     *  @param  konecHry  hodnota false= konec hry, true = hra pokračuje
     */
     public void setKonecHry(boolean konecHry) {
        Hra.konecHry = konecHry;
     }
    
     /**
     *  Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     *  kde se jejím prostřednictvím získává aktualní místnost hry.
     *  
     *  @return     odkaz na herní plán
     */
     public HerniPlan getHerniPlan(){
        return this.herniPlan;
     }

    /**
     * Getter pro hlavní postavu (character)
     * @return odkaz na hlavní postavu
     */
     public MainCharacter getCharacter() {
         return this.character;
     }

    /**
     * Getter pro seznam platných příkazů
     * @return seznam platných příkazů
     */
     public SeznamPrikazu getPlatnePrikazy() {
         return this.platnePrikazy;
     }
    
}

