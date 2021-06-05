package logika.prikazy;

import logika.Hra;
import logika.Prostor;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *  Třída PrikazJdi implementuje pro hru příkaz jdi.
 *  Tato třída je součástí jednoduché textové hry.
 *  
 *@author     Jarmila Pavlickova, Luboš Pavlíček
 *@version    pro školní rok 2016/2017
 */
public class PrikazJdi extends Prikaz {

    public PrikazJdi(Hra hra) {
        super(hra);
    }

    /**
     *  Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     *  existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     *  (východ) není, vypíše se chybové hlášení.
     *
     *@param parametry - jako  parametr obsahuje jméno prostoru (východu),
     *                         do kterého se má jít.
     *@return zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední prostor), tak ....
            return "Kam mám jít? Musíš zadat jméno východu";
        }

        StringBuilder s = new StringBuilder();
        Arrays.stream(parametry).collect(Collectors.toList()).forEach(arg -> s.append(arg + " "));
        String smer = s.toString().strip();

        // zkoušíme přejít do sousedního prostoru
        Prostor sousedniProstor = this.getHerniPlan().getAktualniProstor().vratSousedniProstor(smer);

        if (sousedniProstor == null) return "Tam se odsud jít nedá!";

        this.getHerniPlan().setAktualniProstor(sousedniProstor);
        return sousedniProstor.dlouhyPopis();
    }
    
    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @return nazev prikazu
     */
    @Override
    public String getNazev() {
        return "jdi";
    }

}
