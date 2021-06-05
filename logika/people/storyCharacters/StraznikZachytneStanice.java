package logika.people.storyCharacters;

import logika.Prostor;
import logika.items.Item;
import logika.people.Interactable;
import logika.people.MainCharacter;
import logika.people.Enemy;

import java.util.Arrays;

public class StraznikZachytneStanice extends Enemy implements Interactable {

    private final Item obcanka;
    private final Prostor zachytnaStanice;
    private final Prostor uvnitrZachytneStanice;

    private int interactOption;
    private final boolean[] viable;

    public StraznikZachytneStanice(String name, int baseAttack, Item obcanka, Prostor zachytnaStanice, Prostor uvnitrZachytneStanice) {
        super(name, baseAttack);
        this.obcanka = obcanka;
        this.zachytnaStanice = zachytnaStanice;
        this.uvnitrZachytneStanice = uvnitrZachytneStanice;

        this.interactOption = 0;

        this.viable = new boolean[2];
        Arrays.fill(this.viable, true);
    }

    @Override
    public String interact(MainCharacter interacter) {
        boolean obcankaPresent = interacter.getInventory().contains(obcanka);

        if (this.interactOption == 0) {
            return "Co chceš?! "
                    + (this.viable[0] ? "Dovnitř tě pustim jenom, když mi ukážeš občanku!" : "Přístup dovnitř už máš!")
                    + (this.viable[1] && obcankaPresent ? System.lineSeparator() + "1 - Tady máš občanku!" : "");

        }

        if (obcankaPresent && this.viable[0] && interactOption == 1) {
            zachytnaStanice.setVychod(uvnitrZachytneStanice);
            uvnitrZachytneStanice.setVychod(zachytnaStanice);
            this.viable[0] = false;
            return "Prosím - můžete vstoupit";
        }

        this.interactOption = 0;
        return "Takovou možnost neznám";
    }

    @Override
    public String interact(MainCharacter interacter, int option) {
        this.interactOption = option;
        return this.interact(interacter);
    }

    @Override
    protected String getLosingText(int remainingHealth) {
        return "Útočit na strážníka? Jako vážně? Strážníku zbylo " + remainingHealth + " životů.";
    }
}
