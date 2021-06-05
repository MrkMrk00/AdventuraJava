package logika.prikazy;

import logika.Hra;


/**
 * Třída příkazu nápověda <br>
 * Metoda provedPrikaz vrátí cíl hry a seznam příkazů ve formě String pro vypsání do konzole
 */
public class PrikazNapoveda extends Prikaz {

    public PrikazNapoveda(Hra hra) {
        super(hra);
    }

    /**
     *  Vrací základní nápovědu po zadání příkazu "napoveda". Nyní se vypisuje
     *  vcelku primitivní zpráva a seznam dostupných příkazů.
     *  
     *  @return napoveda ke hre
     */
    @Override
    public String provedPrikaz(String... parametry) {
        return """
               Tvým úkolem je získat dostatečné finanční prostředky
               pro zaplacení ubytování na záchytné stanici.
               
               Můžeš použít tyto příkazy:
               """
        + this.getPlatnePrikazy().vratNazvyPrikazu();
    }
    
     /**
     *  @return nazev příkazu
     */
    @Override
      public String getNazev() {
        return "nápověda";
     }

}
