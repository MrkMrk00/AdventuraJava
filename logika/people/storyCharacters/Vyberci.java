package logika.people.storyCharacters;

import logika.Hra;
import logika.people.Interactable;
import logika.people.MainCharacter;
import logika.people.Person;

import java.util.Arrays;

public class Vyberci extends Person implements Interactable {
    private int interactOption;
    private final boolean[] viable;

    public Vyberci(String name, int baseAttack) {
        super(name, baseAttack);

        this.interactOption = 0;

        this.viable = new boolean[1];
        Arrays.fill(this.viable, false);
    }

    @Override
    public String interact(MainCharacter interacter) {
        boolean enoughMoney = interacter.getMoney() >= 2500;

        if (this.interactOption == 0) {
            if (enoughMoney) viable[0] = true;
            return "Dobrý den, vy tu jste asi kvůli zaplacení poplatku za přespání, že?"
                    + (enoughMoney ? "1 - Ano, tady máte peníze, pane!" : "");
        }


        //konec hry - postava má dost financí na zaplacení pobytu
        if (this.viable[0] && this.interactOption == 1) {
            interacter.spendMoney(2500);
            Hra.konecHry = true;
            return "Dobře - je to přesně. Už u nás nemáte žádný dluh!";
        }

        this.interactOption = 0;
        return "Takovou možnost neznám.";
    }

    @Override
    public String interact(MainCharacter interacter, int option) {
        this.interactOption = option;
        return this.interact(interacter);
    }
}
