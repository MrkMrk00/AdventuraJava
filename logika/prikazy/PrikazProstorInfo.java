package logika.prikazy;

import logika.Hra;

public class PrikazProstorInfo extends Prikaz {

    public PrikazProstorInfo(Hra hra) {
        super(hra);
    }

    @Override
    public String provedPrikaz(String... parametry) {
        return this.getHerniPlan().getAktualniProstor().dlouhyPopis();
    }

    @Override
    public String getNazev() {
        return "prostorinfo";
    }
}
