package logika.people;

import logika.Hra;

public class MainCharacter extends Person {
    private double money;
    private final Hra hra;

    /**
     * Konstruktor hlavní postavy hry<br>
     * Instance hl. postavy je vytvářena jednou při vytváření hry
     * @param hra propsání instance hry
     * @param baseAttack základní hodnota útoku postavy
     */
    public MainCharacter(Hra hra, int baseAttack) {
        super("Hlavni postava", baseAttack);
        this.money = 0;
        this.hra = hra;
    }

    /**
     * Metoda přidá peníze do peněženky hlavní postavy
     * @param amount množství peněz, které se má přidat
     */
    public void addMoney(double amount) {
        this.money += amount;
    }

    /**
     * Metoda utratí peníze z peněženky hlavní postavy<br>
     * Obsahuje logiku, aby množství peněz nešlo pod 0
     * @param amount množství k utracení
     * @return boolean - true, když se podaří odebrat takové množství peněz
     */
    public boolean spendMoney(double amount) {
        if (this.money - amount < 0 || amount < 0) return false;
        else this.money -= amount;

        return true;
    }

    @Override
    public boolean checkLiving() {
        if (!super.checkLiving()) {
            hra.setKonecHry(true);
        }

        return this.isLiving();
    }

    /**
     * Getter
     * @return množství peněz v peněžence hlavní postavy
     */
    public double getMoney() {
        return money;
    }

}
