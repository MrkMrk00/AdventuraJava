package logika.people;

/**
 * Třída běžného nepřítele<br>
 * Implementuje metodu attack(Person attacker) z interface Attackable
 */
public class Enemy extends Person implements Attackable {

    public Enemy(String name, int baseAttack) {
        super(name, baseAttack);
    }

    protected String getWinningText(int remainingHealth) {
        return "Vyhrál jsi! Zbylo ti " + remainingHealth + " života.";
    }

    protected String getLosingText(int remainingHealth) {
        return "Souboj si prohrál. Nepříteli zbylo " + remainingHealth + " života.";
    }

    @Override
    public String attack(MainCharacter attacker) {

        int thisAttackIndex = (attacker.getHealth() % this.getAttack() == 0
                ? attacker.getHealth()/this.getAttack()
                : attacker.getHealth()/this.getAttack() + 1);
        int attackerAttackIndex = (this.getHealth() % attacker.getAttack() == 0
                ? this.getHealth() / attacker.getAttack()
                : this.getHealth() / attacker.getAttack() + 1);


        //Když vyhraje Enemy
        if (thisAttackIndex <= attackerAttackIndex) {
            attacker.changeHealth(-100);
            this.changeHealth(-thisAttackIndex * attacker.getAttack());
            this.callAfterAttack();
            return this.getLosingText(this.getHealth());
        }

        //když vyhraje attacker
        attacker.changeHealth(-attackerAttackIndex * this.getAttack());
        this.changeHealth(-100);
        attacker.callAfterAttack();
        return this.getWinningText(attacker.getHealth());
    }
}
