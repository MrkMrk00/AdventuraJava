package logika.people.storyCharacters;

import logika.items.Item;
import logika.people.Enemy;
import logika.people.Interactable;
import logika.people.MainCharacter;

import java.util.Arrays;

/**
 * Třída jedné z příběhových postav - Prodavač benzínky<br>
 * Postava pracuje s příběhovým předmětem - občankou (založen v herním plánu a propsán do konstruktoru)<br>
 * S postavou je možné konverzovat - témata, o kterých je stále možné konverzovat, jsou řízeny proměnnou boolean[] viable.
 * Uživatelem zvolená možnost se ukládá do proměnné interactOption.
 */
public class ProdavacBenzinky extends Enemy implements Interactable {

    private int interactOption;
    private final boolean[] viable;

    private final Item obcanka;

    public ProdavacBenzinky(String name, int baseAttack, Item obcanka) {
        super(name, baseAttack);

        this.obcanka = obcanka;

        this.interactOption = -1;

        this.viable = new boolean[3];
        Arrays.fill(this.viable, false);
    }


    @Override
    public String interact(MainCharacter interacter) {
        if (!this.viable[0]) {
            this.interactOption = 0;
            this.viable[0] = true;
            this.viable[1] = true;
        }

        switch (this.interactOption) {
            case 0:
                return "Co tady děláš?! Včera jsi tady dělal pěknej bordel!!!"
                    + (this.viable[1] ? System.lineSeparator() + "1 - Vo čem to mluvíš?" : "")
                    + (this.viable[2] ? System.lineSeparator() + "2 - A nenašlo se tady něco včera?" : "");

            case 1:
                if (!this.viable[1]) break;
                this.interactOption = 0;
                this.viable[2] = true;
                return """
                    No včera jste s tim druhym bezďákem našli někde kilo, tak ste
                    si tu koupily nějakou vodku a exovali jste...
                    """;

            case 2:
                if (!this.viable[2]) break;
                this.interactOption = 0;
                interacter.getInventory().add(this.obcanka);
                return "Ale jo, našla se tady nějaká občanka... Můžu ti jí ukázat, jestli je tvoje";

        }
        return "Takovou možnost neznám";
    }

    @Override
    public String interact(MainCharacter interacter, int option) {
        this.interactOption = option;
        return this.interact(interacter);
    }
}
