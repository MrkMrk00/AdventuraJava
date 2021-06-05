package logika.people.storyCharacters;

import logika.Prostor;
import logika.items.Item;
import logika.items.TransformingItem;
import logika.items.Weapon;
import logika.people.Interactable;
import logika.people.MainCharacter;
import logika.people.Person;

import java.util.Arrays;


/**
 * Třída jedné z příběhových postav - Franta<br>
 * Franta vyměňuje cigaretu (příběhový předmět založen v herním plánu a propsán do konstruktoru) za lahváč<br>
 * Franta taktéž ukazuje hlavní postavě cestu do města (propsáno do konstruktoru)<br>
 * S postavou je možné konverzovat - témata, o kterých je stále možné konverzovat, jsou řízeny proměnnou boolean[] viable.
 * Uživatelem zvolená možnost se ukládá do proměnné interactOption.
 */
public class Franta extends Person implements Interactable {

    private int interactOption;
    private final boolean[] viable;

    private final Item vajnos;
    private final Item vajnos2;
    private final Prostor zachytnaStanice;
    private final Prostor mesto;

    /**
     * Konstruktor příběhové postavy Franta.<br>
     * Postava pracuje s příběhovými předměty - jsou založené v konstruktoru HerniPlan a propsány do tohoto konstruktoru.<br>
     * Postava propojuje prostory hry mesto a zachytnaStanice, které jsou vytvořeny v konstruktoru HerniPlan a propsány sem.
     * @param name jméno postavy
     * @param baseAttack základní útok postavy
     * @param vajnos příběhový předmět
     * @param vypitejVajnos příběhový předmět
     * @param zachytnaStanice prostor k propojení
     * @param mesto prostor k propojení
     */
    public Franta(String name, int baseAttack, Item vajnos, Item vypitejVajnos, Prostor zachytnaStanice, Prostor mesto) {
        super(name, baseAttack);
        this.vajnos = vajnos;
        this.zachytnaStanice = zachytnaStanice;
        this.mesto = mesto;
        this.vajnos2 = vypitejVajnos;

        this.interactOption = -1;

        this.viable = new boolean[3];
        Arrays.fill(this.viable, false);
    }

    /**
     * Obsahuje logiku konverzace postavy
     * @param interacter postava, která s danou mluví (MainCharacter)
     * @return String k vypsání do konzole
     */
    @Override
    public String interact(MainCharacter interacter) {
        Item vajnosToRemove = (this.vajnos == null ? this.vajnos2 : this.vajnos);
        this.viable[2] = interacter.getInventory().contains(vajnosToRemove);

        if (!this.viable[2] && this.interactOption == 2) this.interactOption = 0;

        if (!viable[0]) this.interactOption = -1;

        switch (this.interactOption) {
            case -1:
                this.viable[0] = true;
                this.viable[1] = true;
                this.interactOption = 0;

            case 0:
                return """
                        Čau kámo! Taky jsi to včera úplně nedal?
                        No, to je jedno... Co potřebuješ, kámo?
                           (vybírej pomocí příkazu 'mluv *jméno postavy* - *číslo možnosti*')
                        """
                        + System.lineSeparator() + "1 - Nevíš jak se dostanu do města?"
                        + (this.viable[2] ? System.lineSeparator() + "2 - Tady máš vajnos!" : "");

            case 1:
                if (this.viable[1]) {
                    //přidá do inventáře postavy TransformingItem, který se po použití přemění na zbraň
                    interacter.getInventory().add(
                            new TransformingItem("Prázdnej lahváč",
                                    new Weapon("Rozbitej lahváč (zbraň)",
                                            20,
                                            true
                                    ),
                                    interacter
                            )
                    );
                }

                String toReturn =
                        """
                        No jasně, že vim... Ale nebude to zadarmo.
                        Včera - předtim než mě odvezli - tak mi Tomáš ukradl vajnos. Myslim, že
                        jsem ho zahlídl před chvíli vedle v uličce... Kdybys ho dokázal nějak
                        překecat (víš jak ;) ), tak bych ti byl vděčnej.
                        """
                        + (this.viable[1] ? System.lineSeparator() + "Tady máš něco, co bys k tomu mohl použít." : "");

                this.interactOption = 0;
                this.viable[1] = false;
                return toReturn;

            case 2:
                interacter.getInventory().remove(vajnosToRemove);
                this.zachytnaStanice.setVychod(this.mesto);
                this.mesto.setVychod(this.zachytnaStanice);
                return """
                        Tyjo! Jaks to dokázals? Já jsem se ho snažil překecávat snad hodinu a nechtěl mi ho dát.
                        No nic, díky moc. Do města se jde tudy... (použij příkaz prostorinfo pro vypsání východů)
                        """;
        }
        return "Takovou možnost nanabízím.";
    }

    /**
     * Nastavuje proměnnou interactOption a volá this.interact(interacter)
     * @param interacter postava, která s danou mluví (MainCharacter)
     * @param option nastavení možnosti odpovědi
     * @return String k vypsání do konzole
     */
    @Override
    public String interact(MainCharacter interacter, int option) {
        this.interactOption = option;
        return this.interact(interacter);
    }

}
